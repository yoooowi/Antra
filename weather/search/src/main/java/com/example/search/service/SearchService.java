package com.example.search.service;

import com.example.search.entity.SearchResult;

import java.util.List;

public interface SearchService {

    List<SearchResult> search(String title);
}
