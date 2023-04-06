package com.yatsevich.citylist.api.adapter.in.service.city.mapper;

import com.yatsevich.citylist.api.adapter.in.service.city.dto.CityDto;
import com.yatsevich.citylist.api.adapter.in.service.city.dto.UpdateCityRequestDto;
import com.yatsevich.citylist.api.domain.model.City;
import org.mapstruct.Mapper;

import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = "spring", unmappedTargetPolicy = IGNORE)
public interface DtoCityMapper {

  CityDto toCityDto(City city);

  City fromUpdateCityRequestDto(UpdateCityRequestDto requestDto);

}
