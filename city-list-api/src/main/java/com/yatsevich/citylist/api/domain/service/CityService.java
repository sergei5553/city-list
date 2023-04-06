package com.yatsevich.citylist.api.domain.service;

import java.util.Optional;

import javax.naming.OperationNotSupportedException;

import org.springframework.data.domain.Page;

import com.yatsevich.citylist.api.domain.model.City;

public interface CityService {

  void populateDefaultData();

  void updateCity(City city) throws OperationNotSupportedException;

  Page<City> findCities(Optional<String> name, Integer pageNumber, Integer pageSize);

  long count();
}
