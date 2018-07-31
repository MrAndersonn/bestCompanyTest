package com.test.bestCompanyTest;

import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@SpringBootApplication
public class BestCompanyTestApplication  {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        allowAccessAllSymbolsForHttpParser();
        SpringApplication.run(BestCompanyTestApplication.class, args);
    }

    private static void allowAccessAllSymbolsForHttpParser() throws NoSuchFieldException, IllegalAccessException {
        boolean[] newAccessSymbol = new boolean[128];

        for (int i = 0; i < newAccessSymbol.length; i++) {
            newAccessSymbol[i] = true;
        }

        Field request_target_allow = HttpParser.class.getDeclaredField("REQUEST_TARGET_ALLOW");
        request_target_allow.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(request_target_allow, request_target_allow.getModifiers() & ~Modifier.FINAL);

        request_target_allow.set(null, newAccessSymbol);
    }
}
