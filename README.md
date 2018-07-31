[![Build Status](https://travis-ci.org/MrAndersonn/bestCompanyTest.svg?branch=master)](https://travis-ci.org/MrAndersonn/bestCompanyTest)

# *UNKNOWN COMPANY* TEST


## Summary
In this test i found a problem with parsing class that is used by ```tomcat``` called `HttpParser` 
that can'n work with special symbols as parameter query. I use `Spring Boot` and cannot configure of this property.
Anyway i use `reflection` and set that all symbols for parameter are available for path.
I think it's not best practice, but for test it's fine.

# Realisation
## Pageble
I use 2 strategies for this endpoint based on parameters that we receive.
In the case when controllers receive ``Pageble`` we return a page with result.
Example: If parameters are ``page = 0; size = 1000`` we retrieve a 1000 contacts
but we will process data as long as we don't find 1000 contacts that accept criteria.

## UnPageble
For request without ``pageble`` we have global pipeline, that's mean if we receive a one million requests
we are listening one pipeline and will proceed as one. 
```
!!! BUT WE PROCCED ALL DB DOCUMENTS.
    IT'S WAY FOR SMALL BUT FREQUENT REQUESTS
```

Also I use DOA pattern and use @ConditionalOnProperty for specify current dao(set in config). Use ``Spring Data`` for working with psql.


# GETTING START 
* ### first step
```
vagrant up
```
* ### second step

#### For windows
```
    mvnw.cmd clean install
    mvnw.cmd spring-boot:run
```

#### For windows
```
    ./mvnw clean install
    ./mvnw.cmd spring-boot:run
```

**After that you need only waiting for starting application. BD will had been populate before tomcat would start to run**
