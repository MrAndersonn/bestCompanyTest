package com.test.bestCompanyTest.dao;

import com.test.bestCompanyTest.models.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ContactDao {

    void save(Contact entity);

    Page<Contact> findAllPageble(Pageable pageble);
}
