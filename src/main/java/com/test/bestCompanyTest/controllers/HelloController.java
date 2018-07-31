package com.test.bestCompanyTest.controllers;

import com.test.bestCompanyTest.models.Contact;
import com.test.bestCompanyTest.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.test.bestCompanyTest.utils.UriUtils.getParametrsFromUri;
import static com.test.bestCompanyTest.utils.UriUtils.tryRetrivePagebleFromParam;


@RestController
@RequestMapping("/hello")
public class HelloController {

    private static final String CONTACTS_JSON_KEY = "contacts";
    private static final String NAME_FILTER_PARAM_KEY = "nameFilter";

    @Autowired
    private ContactService contactService;

    @GetMapping("/contacts*")
    public Map<String, List<Contact>> retrieveContacts(HttpServletRequest request) throws InterruptedException {

        Map<String, String> parametersFromUri = getParametrsFromUri(request.getQueryString());

        Map<String, List<Contact>> filteredContacts = new HashMap<>();
        filteredContacts.put(CONTACTS_JSON_KEY,
                contactService.retrieveContactsWithFilter(
                        parametersFromUri.get(NAME_FILTER_PARAM_KEY),
                        tryRetrivePagebleFromParam(parametersFromUri)
                )
        );
        return filteredContacts;
    }
}
