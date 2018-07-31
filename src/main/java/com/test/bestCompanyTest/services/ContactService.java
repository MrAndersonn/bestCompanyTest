package com.test.bestCompanyTest.services;

import com.test.bestCompanyTest.dao.ContactDao;
import com.test.bestCompanyTest.models.Contact;
import com.test.bestCompanyTest.services.jobs.ContactJob;
import com.test.bestCompanyTest.services.jobs.PagedContactJob;
import com.test.bestCompanyTest.services.jobs.UnpagedContactJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    @Autowired
    private JobExecutor jobExecutor;

    @Autowired
    private ContactDao contactDao;

    public List<Contact> retrieveContactsWithFilter(String filter, Pageable pageable) throws InterruptedException {
        ContactJob job = pageable == null ? new UnpagedContactJob(filter) : new PagedContactJob(filter, pageable);
        jobExecutor.placeAndReturnJob(job);
        return job.getResult();
    }

    public void saveContact(Contact contact) {
        contactDao.save(contact);
    }
}