package com.yatsevich.citylist.api.adapter.in.service.city;

import static com.yatsevich.citylist.api.boot.security.UserAuthority.HAS_ACCESS;
import static com.yatsevich.citylist.api.boot.security.UserAuthority.HAS_FULL_ACCESS;

import java.util.Optional;

import javax.naming.OperationNotSupportedException;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import com.yatsevich.citylist.api.adapter.in.CityApiController;
import com.yatsevich.citylist.api.adapter.in.service.city.dto.GetCitiesResponseDto;
import com.yatsevich.citylist.api.adapter.in.service.city.dto.UpdateCityRequestDto;
import com.yatsevich.citylist.api.adapter.in.service.city.mapper.DtoCityMapper;
import com.yatsevich.citylist.api.domain.model.City;
import com.yatsevich.citylist.api.domain.service.CityService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CityApiService implements CityApiController {

  private final CityService cityService;
  private final DtoCityMapper dtoCityMapper;

  @PreAuthorize(HAS_ACCESS)
  @Override
  public ResponseEntity<GetCitiesResponseDto> findCities(String name, Integer pageNumber, Integer pageSize) {

    Page<City> cities = cityService.findCities(Optional.ofNullable(name), pageNumber, pageSize);

    return new ResponseEntity<>(
        GetCitiesResponseDto.builder()
                .cities(cities.stream().map(dtoCityMapper::toCityDto).toList())
                .pageSize(cities.getSize())
                .pageNumber(cities.getNumber())
                .total(cities.getTotalElements())
                .build(),
        HttpStatus.OK);
  }

  @PreAuthorize(HAS_FULL_ACCESS)
  @Override
  public ResponseEntity<String> updateCity(String id, UpdateCityRequestDto updateRequest) {
    City cityToUpdate = dtoCityMapper.fromUpdateCityRequestDto(updateRequest);
    cityToUpdate.setId(id);

    try {
      cityService.updateCity(cityToUpdate);
    } catch (OperationNotSupportedException e) {
      log.error(e.getMessage());
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Override
  public ResponseEntity<Long> getCitiesAmount() {
    return new ResponseEntity<>(cityService.count(), HttpStatus.OK);
  }
}
