package com.yatsevich.citylist.api.adapter.out.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Builder
@Data
@Document(collection = "cities")
public class CityEntity {

  @Id
  private String id;
  private String name;
  private String photo;
  @Version
  private Long version;
}
