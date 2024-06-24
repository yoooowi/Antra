package com.example.university.controller;


import com.example.university.entity.University;
import com.example.university.service.UniversityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class UniversityController {

    private final UniversityService remoteService;
    private Logger logger = LoggerFactory.getLogger(UniversityController.class);

    @Autowired
    public UniversityController(UniversityService remoteService){
        this.remoteService = remoteService;
    };

    @GetMapping
    public ResponseEntity<University[]> getAll() {
        logger.info("GET /search");
        University[] universities = remoteService.getAll();

        logger.info("GET /search: " + HttpStatus.OK);
        return new ResponseEntity<>(universities, HttpStatus.OK);

    }


    @GetMapping(params = "countries")
    public ResponseEntity<University[]> getByCountries(@RequestParam List<String> countries) {
        logger.info("GET /search " + countries);
        University[] universities = remoteService.getByCountries(countries);

        logger.info("GET /search " + countries + ": " + HttpStatus.OK);
        return new ResponseEntity<>(universities, HttpStatus.OK);
    }
}
