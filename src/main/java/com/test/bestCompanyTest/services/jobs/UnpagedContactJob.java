package com.test.bestCompanyTest.services.jobs;

import com.test.bestCompanyTest.models.Contact;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class UnpagedContactJob extends ContactJob {
    private int startPage;
    private int currentPage;
    private int proceedPage = 0;

    public UnpagedContactJob(String filterPattern) {
        super(filterPattern);
    }

    @Override
    public void processContactPage(Page<Contact> page) {
        if (currentPage == startPage && proceedPage != 0) {
            isDone = true;
            return;
        }

        System.out.println(currentPage + "============");
        getResult().addAll(filteredContactByPattern(page));
        proceedPage++;
        System.out.println(this.result);
    }

    private List<Contact> filteredContactByPattern(Page<Contact> page) {
        return page.getContent()
                .stream()
                .filter(this::matchContactWithPattern)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isDone() {
        return isDone;
    }

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
