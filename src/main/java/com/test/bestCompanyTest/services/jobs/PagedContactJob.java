package com.test.bestCompanyTest.services.jobs;

import com.test.bestCompanyTest.models.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

public class PagedContactJob extends ContactJob {
    private Pageable pageable;
    private int size;

    public PagedContactJob(String filterPattern, Pageable pageable) {
        super(filterPattern);
        this.pageable = pageable;
        this.size = pageable.getPageNumber() == 0 ? pageable.getPageSize() : pageable.getPageSize() * pageable.getPageNumber() + 1;
    }

    @Override
    public void processContactPage(Page<Contact> page) {
        List<Contact> result = this.result;
        for (Contact contact : page) {
            if (!contact.getName().matches(filterPattern)) {
                result.add(contact);
            }

            if (size == result.size()) {
                isDone = true;
                break;
            }

        }
    }

    @Override
    public List<Contact> getResult() {
        int previousPageStart = pageable.getPageSize() * (pageable.getPageNumber());
        List<Contact> result = this.result;

        if (result.size() > previousPageStart) {
            return result.subList(previousPageStart, result.size());
        }

        return Collections.emptyList();
    }

    @Override
    public boolean isDone() {
        return isDone;
    }
}
