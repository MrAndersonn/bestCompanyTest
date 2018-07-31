package com.test.bestCompanyTest.dao.postgresDaoImpl;

import com.test.bestCompanyTest.dao.ContactDao;
import com.test.bestCompanyTest.models.Contact;
import com.test.bestCompanyTest.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
//todo conditional on property
public class PostgresContactDao implements ContactDao {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public void removeAll() {
        contactRepository.deleteAll();
    }

    @Override
    public void save(Contact entity) {
        contactRepository.save(entity);
    }

    @Override
    public void save(List<Contact> entity) {
        contactRepository.saveAll(entity);
    }

    @Override
    public Page<Contact> findAllPageble(Pageable pageble) {
        return contactRepository.findAll(pageble);
    }
}
