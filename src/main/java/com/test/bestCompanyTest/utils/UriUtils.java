package com.test.bestCompanyTest.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class UriUtils {
    public static Map<String, String> getParametrsFromUri(String uri) {
        return Arrays.stream(uri.split("&"))
                .map(paramAndValue -> paramAndValue.split("="))
                .filter(paramAndValue -> paramAndValue.length == 2)
                .collect(Collectors.toMap(paramAndValue -> paramAndValue[0], paramAndValue -> paramAndValue[1]));
    }

    public static Pageable tryRetrivePagebleFromParam(Map<String, String> parametrs) {
        if (parametrs.get("page") == null || parametrs.get("size") == null) return null;

        return PageRequest.of(
                Integer.parseInt(parametrs.get("page")),
                Integer.parseInt(parametrs.get("size"))
        );
    }
}
