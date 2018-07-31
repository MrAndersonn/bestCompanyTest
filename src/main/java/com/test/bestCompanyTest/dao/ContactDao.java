package com.test.bestCompanyTest.dao;

import com.test.bestCompanyTest.models.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ContactDao {

    void removeAll();

    void save(Contact entity);

    void save(List<Contact> entity);

    Page<Contact> findAllPageble(Pageable pageble);
}
