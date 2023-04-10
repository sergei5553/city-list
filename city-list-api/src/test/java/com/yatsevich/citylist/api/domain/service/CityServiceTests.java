package com.yatsevich.citylist.api.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.naming.OperationNotSupportedException;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.yatsevich.citylist.api.adapter.out.persistence.model.CityEntity;
import com.yatsevich.citylist.api.adapter.out.persistence.repository.CityRepository;
import com.yatsevich.citylist.api.domain.mapper.CityMapper;
import com.yatsevich.citylist.api.domain.mapper.CityMapperImpl;
import com.yatsevich.citylist.api.domain.model.City;

@ExtendWith(MockitoExtension.class)
class CityServiceTests {

  @Mock
  private CityRepository cityRepository;

  @Spy
  private CityMapper cityMapper = new CityMapperImpl();

  @InjectMocks
  private CityServiceImpl cityService;

  @Test
  void testPopulateDefaultData() throws IOException, CsvValidationException {
    List<CityEntity> defaultCities = new ArrayList<>();
    InputStream inputStream = new ClassPathResource("cities.csv").getInputStream();
    CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
        .withSkipLines(1).build();
    String[] line;
    while ((line = csvReader.readNext()) != null) {
      CityEntity city = CityEntity.builder().id(line[0]).name(line[1]).photo(line[2]).build();
      defaultCities.add(city);
    }

    when(cityRepository.saveAll(defaultCities)).thenReturn(defaultCities);
    cityService.populateDefaultData();
    verify(cityRepository).saveAll(defaultCities);
  }

  @Test
  void testUpdateCity() throws OperationNotSupportedException {
    City city = new City("1", "City 1", "photo1.jpg");
    CityEntity cityEntity = CityEntity.builder().id("1").name("City 1").photo("photo1.jpg").build();
    when(cityRepository.findById(city.getId())).thenReturn(Optional.of(cityEntity));
    when(cityRepository.save(cityEntity)).thenReturn(cityEntity);
    cityService.updateCity(city);
    verify(cityRepository).save(cityEntity);
    assertEquals(city.getName(), cityEntity.getName());
    assertEquals(city.getPhoto(), cityEntity.getPhoto());
  }

  @Test
  void testUpdateCityWithNonExistentCity() {
    City city = new City("1", "City 1", "photo1.jpg");
    when(cityRepository.findById(city.getId())).thenReturn(Optional.empty());
    assertThrows(OperationNotSupportedException.class, () -> cityService.updateCity(city));
  }

  @Test
  void testFindCities() {
    List<CityEntity> cityEntities = new ArrayList<>();
    cityEntities.add(CityEntity.builder().id("1").name("City 1").photo("photo1.jpg").build());
    cityEntities.add(CityEntity.builder().id("2").name("City 2").photo("photo2.jpg").build());
    Page<CityEntity> page = new PageImpl<>(cityEntities);
    when(cityRepository.findAll(any(Pageable.class))).thenReturn(page);
    List<City> cities = new ArrayList<>();
    cities.add(new City("1", "City 1", "photo1.jpg"));
    cities.add(new City("2", "City 2", "photo2.jpg"));
    Page<City> result = cityService.findCities(Optional.empty(), 0, 10);
    assertEquals(cities, result.getContent());
  }

  @Test
  void testCount() {
    when(cityRepository.count()).thenReturn(10L);
    long result = cityService.count();
    assertEquals(10L, result);
  }

}
