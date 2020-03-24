package org.ril.hrss.service.org_management;

import org.ril.hrss.model.Country;
import org.ril.hrss.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public List<Country> getAllCountries() {
        List<Country> countries = countryRepository.findAll();
        return countries;
    }
}
