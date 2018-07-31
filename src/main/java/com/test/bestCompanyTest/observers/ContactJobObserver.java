package com.test.bestCompanyTest.observers;

import com.test.bestCompanyTest.models.Contact;
import com.test.bestCompanyTest.services.jobs.UnpagedContactJob;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ContactJobObserver {
    private ReentrantLock lock = new ReentrantLock();
    private List<UnpagedContactJob> listeners = Collections.synchronizedList(new ArrayList<>());

    public void addListener(UnpagedContactJob job) {
        lock.lock();
        listeners.add(job);

        lock.unlock();
    }

    public synchronized void produceEvent(Page<Contact> contactPage, int currentPage) {

        Iterator<UnpagedContactJob> iterator = listeners.iterator();
        while (iterator.hasNext()) {
            UnpagedContactJob contactJob = iterator.next();

            contactJob.setCurrentPage(currentPage);
            contactJob.processContactPage(contactPage);
            if (contactJob.isDone()) {
                iterator.remove();
                synchronized (contactJob) {
                    contactJob.notify();
                }
            }
        }
    }

    public boolean isEmptyListeners() {
        return listeners.isEmpty();
    }
}
