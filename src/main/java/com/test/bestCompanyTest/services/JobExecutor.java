package com.test.bestCompanyTest.services;

import com.test.bestCompanyTest.services.jobs.ContactJob;
import com.test.bestCompanyTest.services.jobs.PagedContactJob;
import com.test.bestCompanyTest.services.jobs.UnpagedContactJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobExecutor {

    @Autowired
    private ContactBatchingService contactBatchingService;

    /**
     * It`s something like factory method, bad implementation, but logic the same.
     * @param job
     * @throws InterruptedException
     */
    void placeAndReturnJob(ContactJob job) throws InterruptedException {
        if (job instanceof UnpagedContactJob) {
            contactBatchingService.registerUnpagedJob((UnpagedContactJob) job);
        } else if (job instanceof PagedContactJob) {
            contactBatchingService.proceedPagedJob(job);
        }
    }
}
