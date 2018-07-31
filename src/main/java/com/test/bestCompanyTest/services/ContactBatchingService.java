package com.test.bestCompanyTest.services;

import com.test.bestCompanyTest.dao.ContactDao;
import com.test.bestCompanyTest.models.Contact;
import com.test.bestCompanyTest.observers.ContactJobObserver;
import com.test.bestCompanyTest.services.jobs.ContactJob;
import com.test.bestCompanyTest.services.jobs.UnpagedContactJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Service for batching sql request on contacts table
 */
@Service
public class ContactBatchingService {

    private ReentrantLock lock = new ReentrantLock();

    private static final int COUNT_OF_THREADS = 2;
    private final int DEFAULT_BATCH_SIZE = 2000;

    private AtomicInteger currentGlobalPage = new AtomicInteger(0);
    private volatile boolean inProgress = false;

    private AtomicInteger availableThread = new AtomicInteger(COUNT_OF_THREADS);

    @Autowired
    private ContactDao contactDao;

    private ContactJobObserver jobObserver = new ContactJobObserver();

    private ExecutorService executor = Executors.newFixedThreadPool(COUNT_OF_THREADS);

    /**
     * Run threads that process pipeline of execute all document in collection, if
     * it is not runes.
     * And register UnpagedContactJob for listening global pipeline
     *
     * @param job
     * @throws InterruptedException
     */
    void registerUnpagedJob(UnpagedContactJob job) throws InterruptedException {
        jobObserver.addListener(job);

        job.setStartPage(currentGlobalPage.get());

        if (!inProgress) {
            runThreads();
        }

        synchronized (job) {
            job.wait();
        }
    }

    /**
     * Process simple job while their page is not be fulled
     *
     * @param job
     */
    void proceedPagedJob(ContactJob job) {
        int current = 0;
        boolean hasMoreContent = true;
        while (hasMoreContent) {
            Page<Contact> currentPage = getContactPage(current++, DEFAULT_BATCH_SIZE);
            job.processContactPage(currentPage);

            hasMoreContent = currentPage.hasNext();
            if (job.isDone()) break;
        }
    }

    /**
     * Paged method for retrieving page for collection
     *
     * @param page
     * @param size
     * @return
     */
    private Page<Contact> getContactPage(int page, int size) {
        return contactDao.findAllPageble(PageRequest.of(page, size));
    }

    private void runThreads() {
        inProgress = true;
        getWorkers().forEach(
                runnable -> executor.submit(runnable));
    }

    private List<Runnable> getWorkers() {
        List<Runnable> workers = new ArrayList<>();
        while (availableThread.get() > 0) {
            workers.add(getWorker());
            availableThread.getAndDecrement();
        }

        return workers;
    }

    /**
     * Create new worker that will be take part in main pipeline
     *
     * @return Runnable Worker
     */
    private Runnable getWorker() {
        return () -> {
            while (!jobObserver.isEmptyListeners()) {
                try {
                    lock.lock();
                    int current = currentGlobalPage.get();
                    Page<Contact> page = getContactPage(currentGlobalPage.getAndIncrement(), DEFAULT_BATCH_SIZE);
                    lock.unlock();

                    processPage(page, current);
                    if (!page.hasNext()) currentGlobalPage = new AtomicInteger(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            lock.lock();
            availableThread.getAndIncrement();
            checkProgressWorkers();
            lock.unlock();
        };
    }

    /**
     * That method set main pipeline is not progress if all threads set as free and
     * have not more job, and send global pipeline index in start.
     */
    private void checkProgressWorkers() {
        inProgress = availableThread.get() != COUNT_OF_THREADS;
        if (!inProgress) currentGlobalPage = new AtomicInteger(0);
    }

    /**
     * Emit Page for observer for the unpaged Job
     *
     * @param page
     * @param currentPage
     */
    private void processPage(Page<Contact> page, int currentPage) {

        if (page.getTotalElements() == 0) {
            currentGlobalPage = new AtomicInteger(0);
        }

        jobObserver.produceEvent(page, currentPage);
    }
}
