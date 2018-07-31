package com.test.bestCompanyTest.eventListener;

import com.test.bestCompanyTest.dao.ContactDao;
import com.test.bestCompanyTest.models.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Component
public class ApplicationStartupListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final int ENTITY_COUNT = 10000;
    public static final int BATCH_COUNT = 1000;
    private Logger LOG = LoggerFactory.getLogger(ApplicationStartupListener.class);

    @Autowired
    private ContactDao contactDao;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        contactDao.removeAll();

        List<Contact> buff = new ArrayList<>();
        for (int i = 0; i <= ENTITY_COUNT; ++i) {
            buff.add(new Contact(UUID.randomUUID().toString().substring(0, 20)));
            if (i % BATCH_COUNT == 0 && i != 0) {
                contactDao.save(buff);
                buff = new ArrayList<>();
                LOG.info("Successful create, new batch of {} contacts: {}", BATCH_COUNT, i);
            }
        }
    }
}
