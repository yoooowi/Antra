package com.example.rest;

import com.example.rest.entity.University;
import com.example.rest.exception.RemoteAPIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@Component
public class CustomTemplate extends RestTemplate {

    @Value("${remote.api}")
    private String api;

    private Logger logger = LoggerFactory.getLogger(CustomTemplate.class);

    public ResponseEntity<List<University>> get() throws RemoteAPIException {
        try {
            logger.info("GET " + api);
            ResponseEntity<List<University>> response =
                    exchange(api, HttpMethod.GET, null, new ParameterizedTypeReference<List<University>>() {
                    });

            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("GET " + api + ": " + response.getStatusCode());
                return response;
            } else {
                logger.error("GET " + api + ": " + response.getStatusCode());
                throw new RemoteAPIException("Cannot retrieve data from " + api);
            }
        } catch (Exception e) {
            logger.error("GET " + api + ": " + e.getMessage());
            throw new RemoteAPIException(e.getMessage());
        }

    }

    public ResponseEntity<List<University>> getByCountry(String country) throws RemoteAPIException {
        country = country.replace(' ', '+');
        String api_param = api + "?country=" + country;

        try{
            logger.info("GET " + api_param);
            ResponseEntity<List<University>> response =
                    exchange(api_param, HttpMethod.GET, null, new ParameterizedTypeReference<List<University>>() {});

            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("GET " + api_param + ": " + response.getStatusCode());
                return response;
            } else {
                logger.error("GET " + api_param + ": " + response.getStatusCode());
                throw new RemoteAPIException("Cannot retrieve data from " + api);
            }
        } catch (Exception e) {
            logger.error("GET " + api_param + ": " + e.getMessage());
            throw new RemoteAPIException(e.getMessage());
        }

    }
}
