package com.example.rest.service;


import com.example.rest.entity.University;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RemoteService {

    public List<University> getAll();

    public List<University> getByCountries(List<String> countries);

    public List<University> getByCountry(String country);


}
