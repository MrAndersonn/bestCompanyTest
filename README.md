[![Build Status](https://travis-ci.org/MrAndersonn/bestCompanyTest.svg?branch=master)](https://travis-ci.org/MrAndersonn/bestCompanyTest)

# *UNKNOWN COMPANY* TEST


## Summary
In this test i find a problem with parsing class that used by ```tomcat``` called `HttpParser` 
that can'n work with special symbols as parameter query. I use `Spring Boot` and cannot configure of this property.
Anyway i use `reflection` and set that all symbols for parameter are available for path.
I think it's not best practice, but for test it's fine.

### Realisation
## Pageble
I use 2 strategies for this endpoint based on parameters that we are receive.
If out controllers receive ``Pageble`` we compile a page and return it with result.
Explain: if ``page = 0; size = 1000`` we retrieve a 1000 contacts, in app we can process more than 1000, 
before we are not find contacts that accept criteria.

## UnPageble
For request without ``pageble`` we have global pipeline, that's mean if we receive a one million request
we are listening one pipeline and will be proceed as one. 
```
!!! BUT WE ARE PROCCED ALL DB DOCUMENT.
    ITS FOR SMALL BUT FREQUENT REQUEST
```

Also I use dao pattern, and use @ConditionalOnProperty, for specify current dao(set in config). Use spring data for working with psql.


#GETTING START 
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


After that you need only waiting for starting application. BD populate before tomcat is running
