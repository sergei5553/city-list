package com.yatsevich.citylist.api.adapter.in.service.city.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CityDto {

  @Schema(description = "The unique ID of the city", example = "10")
  private String id;
  @Schema(description = "The name of the city", example = "Manila")
  private String name;
  @Schema(description = "The url of the photo of the city", example = "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Iss016e019375.jpg/330px-Iss016e019375.jpg")
  private String photo;

}
