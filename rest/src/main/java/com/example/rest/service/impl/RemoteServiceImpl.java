package com.example.rest.service.impl;

import com.example.rest.CustomTemplate;
import com.example.rest.entity.University;
import com.example.rest.exception.RemoteAPIException;
import com.example.rest.service.RemoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Array;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class RemoteServiceImpl  implements RemoteService {

    private CustomTemplate template;
    private Logger logger;

    @Autowired
    public RemoteServiceImpl (CustomTemplate template) {
        this.template = template;
        this.logger = LoggerFactory.getLogger(RemoteServiceImpl.class);
    }

    @Override
    public List<University> getAll() {
        try {
            ResponseEntity<List<University>> response = template.get();
            return response.getBody();
        } catch (RemoteAPIException e) {
            return null;
        }
    }

    @Override
    public List<University> getByCountries(List<String> countries) {
        class Fail {
            boolean fail = false;
            void set() {
                fail = true;
            }
        }
        Fail failed = new Fail();

        List<University> universities = new ArrayList<>();
        List<CompletableFuture> cfs = new ArrayList<>();
        for (String country : countries) {
            cfs.add(
                CompletableFuture.supplyAsync(() -> getByCountry(country))
                        .whenComplete((list, ex) -> {
                            if (!failed.fail) {
                                if (ex == null && list != null) {
                                    logger.info("Search for " + country + " returns " + list.size() + " results");
                                    universities.addAll(list);
                                } else if (ex == null && list == null) {
                                    logger.error("Failed to search universities in " + country);
                                    failed.set();
                                } else if (ex != null) {
                                    logger.error(ex.getMessage());
                                    failed.set();
                                }
                            }
                        }));
        }

        for (CompletableFuture cf : cfs) {
            cf.join();
            if (failed.fail) {
                return null;
            }
        }
        return universities;
    }

    @Override
    public List<University> getByCountry(String country) {
        try {
            ResponseEntity<List<University>> response = template.getByCountry(country);
            return response.getBody();
        } catch (RemoteAPIException e) {
            return null;
        }
    }
}
