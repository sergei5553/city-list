package com.yatsevich.citylist.api.adapter.in.service.city.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetCitiesResponseDto {

  @Schema(description = "The list of found cities")
  private List<CityDto> cities;

  @Schema(description = "The current page number")
  private Integer pageNumber;

  @Schema(description = "The total count of cities")
  private Long total;

  @Schema(description = "The page size")
  private Integer pageSize;
}
