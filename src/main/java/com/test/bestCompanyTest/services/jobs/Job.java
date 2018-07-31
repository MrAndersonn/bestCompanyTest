package com.test.bestCompanyTest.services.jobs;

import java.util.List;

public abstract class Job<T> {

    protected boolean isDone = false;

    public abstract List<T> getResult();

    public abstract boolean isDone();
}
