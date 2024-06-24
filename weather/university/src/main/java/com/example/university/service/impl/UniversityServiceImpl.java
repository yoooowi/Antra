package com.example.university.service.impl;

import com.example.university.entity.University;
import com.example.university.exception.RemoteAPIException;
import com.example.university.service.UniversityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class UniversityServiceImpl  implements UniversityService {

    private Logger logger = LoggerFactory.getLogger(UniversityServiceImpl.class);
    private RestTemplate restTemplate;

    @Value("${remote.api}")
    private String api;

    public UniversityServiceImpl() {
        this.restTemplate = new RestTemplate();
    }


    @Override
    public University[] getAll() {
        return getUniversitiesFrom(api);
    }

    @Override
    public University[] getByCountries(List<String> countries) {
        CompletableFuture[] cfs = new CompletableFuture[countries.size()];
        List<University> results = new ArrayList<>();
        int cidx = 0;

        for(String country : countries) {
            cfs[cidx++] =
                    CompletableFuture.supplyAsync(() -> getByCountry(country))
                            .whenComplete((arr, ex) -> {
                                if (ex == null) {
                                    results.addAll(Arrays.stream(arr).collect(Collectors.toList()));
                                }
                            });
        }
        CompletableFuture.allOf(cfs).join();
        return results.toArray(new University[0]);
    }

    @Override
    public University[] getByCountry(String country) {
        country = country.replace(' ', '+');
        String api_param = api + "?country=" + country;
        return getUniversitiesFrom(api_param);
    }

    private University[] getUniversitiesFrom(String url) {
        logger.info("GET " + url);
        try{
            University[] result = restTemplate.getForObject(url, University[].class);
            return result;
        } catch (ResourceAccessException e) {
            logger.error("GET " + url + ": " + e.getMessage());
            e.printStackTrace();
            throw new RemoteAPIException(url, e.getMessage());
        }


    }

}
