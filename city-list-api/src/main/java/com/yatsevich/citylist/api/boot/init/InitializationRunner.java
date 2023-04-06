package com.yatsevich.citylist.api.boot.init;

import com.yatsevich.citylist.api.domain.service.CityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitializationRunner implements CommandLineRunner {

  private static final String INITIAL_DATA_WAS_POPULATED = "Initial data was populated";
  private static final String CITY_REPOSITORY_IS_NOT_EMPTY = "The city repository is not empty. No need to populate initial data.";
  private final CityService cityService;

  @Override
  public void run(String... args) {
    if (cityService.count() == 0) {
      cityService.populateDefaultData();
      log.info(INITIAL_DATA_WAS_POPULATED);
    } else {
      log.info(CITY_REPOSITORY_IS_NOT_EMPTY);
    }
  }
}
