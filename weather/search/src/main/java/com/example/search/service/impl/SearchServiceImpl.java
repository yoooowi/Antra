package com.example.search.service.impl;

import com.example.common.domain.GeneralResponse;
import com.example.search.entity.Book;
import com.example.search.entity.SearchResult;
import com.example.search.entity.SearchType;
import com.example.search.service.SearchService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    private RestTemplate restTemplate;

    @Value("${remote.server.book}")
    private String bookAPI;

    @Value("${remote.server.details}")
    private String detailsAPI;

    @Autowired
    public SearchServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    @HystrixCommand(fallbackMethod = "searchFallback")
    public List<SearchResult> search(String title) {
        List<SearchResult> results = new ArrayList<>();
        CompletableFuture bookCF = CompletableFuture.supplyAsync(() -> {
            return restTemplate.getForObject(bookAPI + "/book/search?title=" + title, Book[].class);
        }).whenComplete((result, ex)-> {
            if (ex == null) {
                results.add(new SearchResult(SearchType.BOOK, result));
            } else {
                log.error("Failed to access book service");
            }
        });

        CompletableFuture detailsCF = CompletableFuture.supplyAsync(() -> {
            return restTemplate.getForObject(detailsAPI + "/details/port", GeneralResponse.class);
        }).whenComplete((result, ex) -> {
            if (ex == null) {
                results.add(new SearchResult(SearchType.WEATHER, result.getData()));
            } else {
                log.error("Failed to access details service");
            }
        });

        CompletableFuture.allOf(bookCF, detailsCF).join();

        return results;

    }

    public List<SearchResult> searchFallback(String title){
        SearchResult result = new SearchResult();
        result.setType(SearchType.ERROR);
        result.setResult("Remote service unavailable");
        List l = new ArrayList();
        l.add(result);
        return l;
    }
}
