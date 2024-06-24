package com.example.search.controller;

import com.example.common.domain.GeneralResponse;
import com.example.search.entity.SearchResult;
import com.example.search.entity.SearchType;
import com.example.search.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.ConnectException;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    // call details/port api and search book by title
    @GetMapping(value = "/weather/search", params = "title")
    public ResponseEntity<GeneralResponse> getDetails(@RequestParam(name = "title") String title) {
        List<SearchResult> results = searchService.search(title);
        GeneralResponse response = new GeneralResponse();
        response.setCode(results.get(0).getType() == SearchType.ERROR ? 503 : 200);
        response.setData(results);
        response.setTimestamp(new Date(System.currentTimeMillis()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @ExceptionHandler({IllegalStateException.class, ConnectException.class})
//    public ResponseEntity<GeneralResponse> serviceUnavailable(Exception ex) {
//        log.error(ex.getMessage());
//        GeneralResponse response = new GeneralResponse();
//        response.setCode(503);
//        response.setData("Service Unavailable");
//        response.setTimestamp(new Date(System.currentTimeMillis()));
//        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
//    }
}
