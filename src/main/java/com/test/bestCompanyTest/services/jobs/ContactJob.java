package com.test.bestCompanyTest.services.jobs;

import com.test.bestCompanyTest.models.Contact;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public abstract class ContactJob extends Job<Contact> {
    List<Contact> result = new ArrayList<>();
    String filterPattern;

    ContactJob(String filterPattern) {
        this.filterPattern = filterPattern;
    }

    public abstract void processContactPage(Page<Contact> page);

    @Override
    public List<Contact> getResult() {
        return result;
    }
}
