package com.example.rest.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class University {

    private String name;
    private List<String> domains;

    @JsonProperty("web_pages")
    private List<String> webPages;

    public University(String name, List<String> domains, List<String> webPages) {
        this.name = name;
        this.domains = domains;
        this.webPages = webPages;
    }

    public String getName() {
        return name;
    }

    public List<String> getDomains() {
        return domains;
    }

    public List<String> getWebPages() {
        return webPages;
    }
}
