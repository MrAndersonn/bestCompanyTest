package com.test.bestCompanyTest.controllers;

import com.test.bestCompanyTest.models.Contact;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;


@RestController
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping("/contacts")
    public List<Contact> retrieveContacts(@RequestParam(name = "nameFilter") String nameFilterPattern) {


        return Collections.emptyList();
    }
}
