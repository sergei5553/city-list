package com.yatsevich.citylist.api.adapter.in;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yatsevich.citylist.api.adapter.in.service.city.dto.GetCitiesResponseDto;
import com.yatsevich.citylist.api.adapter.in.service.city.dto.UpdateCityRequestDto;
import com.yatsevich.citylist.api.adapter.out.persistence.model.CityEntity;
import com.yatsevich.citylist.api.adapter.out.persistence.repository.CityRepository;
import com.yatsevich.citylist.api.boot.security.UserAuthority;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
class CityApiServiceIntegrationTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private CityRepository cityRepository;

  @Value("${cities.version}")
  private String version;

  private String getUrl() {
    return "/api/" + version + "/cities";
  }

  @BeforeEach
  void setup() {
    cityRepository.deleteAll();
  }

  @Test
  @WithMockUser(username = "user")
  void testForbiddenError() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(getUrl())).andExpect(status().isForbidden()).andReturn();
  }

  @Test
  void testUnauthorizedError() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(getUrl())).andExpect(status().isUnauthorized()).andReturn();
  }

  @Test
  @WithMockUser(username = "user", authorities = UserAuthority.READ_ONLY_AUTHORITY)
  void testFindCities() throws Exception {
    // Add some test cities to the repository
    List<CityEntity> cityEntities = new ArrayList<>();
    cityEntities.add(CityEntity.builder().id("1").name("City 1").photo("photo1.jpg").build());
    cityEntities.add(CityEntity.builder().id("2").name("City 2").photo("photo2.jpg").build());
    cityRepository.saveAll(cityEntities);

    // Perform the GET request to the endpoint
    MvcResult result = mockMvc.perform(
        MockMvcRequestBuilders.get(getUrl())
        .param("name", "City 1")
        .param("pageNumber", "0")
        .param("pageSize", "10"))
        .andExpect(status().isOk()).andReturn();

    // Verify the response
    String responseJson = result.getResponse().getContentAsString();
    GetCitiesResponseDto responseDto = new ObjectMapper().readValue(responseJson, GetCitiesResponseDto.class);

    assertEquals(1, responseDto.getCities().size());
    assertEquals(1, responseDto.getTotal());
    assertEquals(0, responseDto.getPageNumber());
    assertEquals(10, responseDto.getPageSize());
    assertEquals("City 1", responseDto.getCities().get(0).getName());
  }

  @Test
  @WithMockUser(username = "admin", authorities = UserAuthority.FULL_AUTHORITY)
  void testUpdateCity() throws Exception {
    // Add a test city to the repository
    CityEntity city = cityRepository.save(CityEntity.builder().id("1").name("City 1").photo("photo").build());
    // Perform the PUT request to the endpoint
    UpdateCityRequestDto requestDto = new UpdateCityRequestDto("1", "Lyon", "photo1");
    MvcResult result = mockMvc.perform(
        MockMvcRequestBuilders.put(getUrl() + "/{id}", city.getId())
           .contentType(MediaType.APPLICATION_JSON)
           .content(new ObjectMapper().writeValueAsString(requestDto)))
           .andExpect(status().isOk()).andReturn();

    // Verify the response
    assertEquals("", result.getResponse().getContentAsString());
    CityEntity cityEntity = cityRepository.findById(city.getId()).get();
    assertEquals("Lyon", cityEntity.getName());
    assertEquals("photo1", cityEntity.getPhoto());
  }

  @Test
  @WithMockUser(username = "admin", authorities = UserAuthority.FULL_AUTHORITY)
  void testUpdateInternalServerError() throws Exception {
    UpdateCityRequestDto requestDto = new UpdateCityRequestDto("1", "Lyon", "photo1");
    mockMvc
        .perform(
            MockMvcRequestBuilders.put(getUrl() + "/{id}", requestDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isInternalServerError());

  }

  @Test
  @WithMockUser(username = "user", authorities = UserAuthority.READ_ONLY_AUTHORITY)
  void testUpdateForbidden() throws Exception {
    // Perform the PUT request to the endpoint
    UpdateCityRequestDto requestDto = new UpdateCityRequestDto("1", "Lyon", "photo");
    mockMvc.perform(
        MockMvcRequestBuilders.put(getUrl() + "/{id}", requestDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(username = "user", authorities = UserAuthority.READ_ONLY_AUTHORITY)
  void testGetCitiesAmount() throws Exception {
    // Add some test cities to the repository
    List<CityEntity> cityEntities = new ArrayList<>();
    cityEntities.add(CityEntity.builder().id("1").name("City 1").photo("photo1.jpg").build());
    cityEntities.add(CityEntity.builder().id("2").name("City 2").photo("photo2.jpg").build());
    cityRepository.saveAll(cityEntities);

    // Perform the GET request to the endpoint
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(getUrl() + "/count"))
            .andExpect(status().isOk())
            .andReturn();

    assertEquals("2", result.getResponse().getContentAsString());
  }
}