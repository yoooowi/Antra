package com.example.university.service;

import com.example.university.entity.University;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UniversityService {

    public University[] getAll();

    public University[] getByCountries(List<String> countries);

    public University[] getByCountry(String country);

}