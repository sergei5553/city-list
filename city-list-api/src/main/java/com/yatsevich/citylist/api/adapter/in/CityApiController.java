package com.yatsevich.citylist.api.adapter.in;

import com.yatsevich.citylist.api.adapter.in.service.city.dto.GetCitiesResponseDto;
import com.yatsevich.citylist.api.adapter.in.service.city.dto.UpdateCityRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/api/${cities.version}/cities")
public interface CityApiController {

  @Operation(summary = "Returns cities", tags = { "cities" })
  @ApiResponse(responseCode = "200", description = "OK", content = {
                  @Content(mediaType = "application/json", schema = @Schema(implementation = GetCitiesResponseDto.class)) })
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<GetCitiesResponseDto> findCities(
      @Parameter(description = "The name of the city", example = "Manila") @RequestParam(value = "name", required = false) final String name,
      @Parameter(description = "The page number", example = "0") @RequestParam(value = "pageNumber", required = false) final Integer pageNumber,
      @Parameter(description = "The page size", example = "10") @RequestParam(value = "pageSize", required = false) final Integer pageSize);

  @Operation(summary = "Updates the city", tags = { "cities" })
  @ApiResponse(responseCode = "200", description = "OK")
  @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<String> updateCity(
      @Parameter(description = "The city ID", example = "10") @PathVariable String id,
      @RequestBody final UpdateCityRequestDto updateRequest);

  @Operation(summary = "Returns the amount of cities", tags = { "cities" })
  @ApiResponse(responseCode = "200", description = "OK", content = {
                  @Content(mediaType = "application/json", schema = @Schema(implementation = Long.class)) })
  @GetMapping("/count")
  ResponseEntity<Long> getCitiesAmount();

}
