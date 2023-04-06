package com.yatsevich.citylist.api.adapter.in.service.city.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UpdateCityRequestDto {

  @Schema(description = "The city id", example = "20")
  private String id;
  @Schema(description = "The city name", example = "Mumbai")
  private String name;
  @Schema(description = "The city photo", example = "https://cdn.pixabay.com/photo/2020/06/02/10/44/mumbai-5250402_1280.jpg")
  private String photo;
}
