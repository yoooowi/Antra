package com.example.rest.controller;


import com.example.rest.entity.University;
import com.example.rest.service.RemoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.rmi.Remote;
import java.util.List;

@RestController
@RequestMapping("/search")
public class APIController {

    private RemoteService remoteService;
    private Logger logger;

    @Autowired
    public APIController(RemoteService remoteService){
        this.remoteService = remoteService;
        logger = LoggerFactory.getLogger(APIController.class);
    };

    @GetMapping
    public ResponseEntity<List<University>> getAll() {
        logger.info("GET /search");
        List<University> universities = remoteService.getAll();

        HttpStatus status = universities == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK;
        logger.info("GET /search: " + status);
        return new ResponseEntity<>(universities, status);

    }


    @GetMapping(params = "countries")
    public ResponseEntity<List<University>> getByCountries(@RequestParam List<String> countries) {
        logger.info("GET /search " + countries);
        List<University> universities = remoteService.getByCountries(countries);

        HttpStatus status = universities == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK;
        logger.info("GET /search " + countries + ": " + status);
        return new ResponseEntity<>(universities, status);
    }
}
