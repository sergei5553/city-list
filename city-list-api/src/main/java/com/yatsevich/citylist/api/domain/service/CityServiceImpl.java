package com.yatsevich.citylist.api.domain.service;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.yatsevich.citylist.api.adapter.out.persistence.model.CityEntity;
import com.yatsevich.citylist.api.adapter.out.persistence.repository.CityRepository;
import com.yatsevich.citylist.api.adapter.out.service.exception.DefaultDataParsingException;
import com.yatsevich.citylist.api.domain.mapper.CityMapper;
import com.yatsevich.citylist.api.domain.model.City;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.naming.OperationNotSupportedException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CityServiceImpl implements CityService {

  public static final String CITY_ADDITION_IS_NOT_SUPPORTED = "The city addition is not supported in this app version.";
  private final CityRepository cityRepository;
  private final CityMapper cityMapper;

  @Value(value = "${cities.defaultPageSize}")
  public Integer defaultPageSize;

  @Override
  public void populateDefaultData() {

    List<CityEntity> defaultCities = new ArrayList<>();

    try (CSVReader csvReader = new CSVReaderBuilder(
        new FileReader(new ClassPathResource("initialdata/cities.csv").getFile().toPath().toString())).withSkipLines(1)
            .build()) {
      String[] line;
      while ((line = csvReader.readNext()) != null) {
        CityEntity book = CityEntity.builder().id(line[0]).name(line[1]).photo(line[2]).build();
        defaultCities.add(book);
      }
      cityRepository.saveAll(defaultCities);
    } catch (IOException | CsvValidationException e) {
      String errorMessage = String.format(DefaultDataParsingException.GENERIC_IO_ERROR_MESSAGE, e.getMessage());
      log.error(errorMessage, e.getMessage());
      throw new DefaultDataParsingException(errorMessage);
    }

  }

  @Override
  public void updateCity(City city) throws OperationNotSupportedException {
    Optional<CityEntity> existingCity = cityRepository.findById(city.getId());
    if (existingCity.isPresent()) {
      CityEntity cityEntity = existingCity.get();
      cityEntity.setName(city.getName());
      cityEntity.setPhoto(city.getPhoto());
      cityRepository.save(cityEntity);
    } else {
      throw new OperationNotSupportedException(CITY_ADDITION_IS_NOT_SUPPORTED);
    }
  }

  @Override
  public Page<City> findCities(Optional<String> name, Integer pageNumber, Integer pageSize) {

    Pageable pageable = PageRequest
        .of(pageNumber != null ? pageNumber : 0, pageSize != null ? pageSize : defaultPageSize);

    Page<CityEntity> entities;
    if (name.isPresent()) {
      entities = cityRepository.findByNameLikeIgnoreCase(name.get(), pageable);
    } else {
      entities = cityRepository.findAll(pageable);
    }

    return new PageImpl<>(entities.map(cityMapper::toCity).toList(),
        PageRequest.of(entities.getNumber(), entities.getSize()), entities.getTotalElements());
  }

  @Override
  public long count() {
    return cityRepository.count();
  }
}
