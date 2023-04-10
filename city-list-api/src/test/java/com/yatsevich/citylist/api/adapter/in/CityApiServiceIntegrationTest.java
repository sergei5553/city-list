package com.yatsevich.citylist.api.adapter.in;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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

@AutoConfigureMockMvc
@ActiveProfiles({ "test" })
class CityApiServiceIntegrationTest extends AbstractIntegrationTest {

  public static final String LYON = "Lyon";
  public static final String PHOTO_1 = "photo1";
  public static final String ID_1 = "1";
  public static final String DELHI = "Delhi";

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private CityRepository cityRepository;

  @Value("${cities.version}")
  private String version;

  private String getUrl() {
    return "/api/" + version + "/cities";
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
    // Perform the GET request to the endpoint
    MvcResult result = mockMvc
        .perform(
            MockMvcRequestBuilders.get(getUrl()).param("name", DELHI).param("pageNumber", "0").param("pageSize", "10"))
        .andExpect(status().isOk()).andReturn();

    // Verify the response
    String responseJson = result.getResponse().getContentAsString();
    GetCitiesResponseDto responseDto = new ObjectMapper().readValue(responseJson, GetCitiesResponseDto.class);

    assertEquals(2, responseDto.getCities().size());
    assertEquals(2, responseDto.getTotal());
    assertEquals(0, responseDto.getPageNumber());
    assertEquals(10, responseDto.getPageSize());
  }

  @Test
  @WithMockUser(username = "admin", authorities = UserAuthority.FULL_AUTHORITY)
  void testUpdateCity() throws Exception {
    // Add a test city to the repository
    CityEntity cityBeforeUpdate = cityRepository.findById(ID_1).get();
    // Perform the PUT request to the endpoint
    UpdateCityRequestDto requestDto = new UpdateCityRequestDto(ID_1, LYON, PHOTO_1);
    MvcResult result = mockMvc
        .perform(
            MockMvcRequestBuilders.put(getUrl() + "/{id}", cityBeforeUpdate.getId())
                .contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(requestDto)))
        .andExpect(status().isOk()).andReturn();

    // Verify the response
    assertEquals("", result.getResponse().getContentAsString());
    CityEntity cityAfterUpdate = cityRepository.findById(cityBeforeUpdate.getId()).get();
    assertEquals(LYON, cityAfterUpdate.getName());
    assertEquals(PHOTO_1, cityAfterUpdate.getPhoto());
    assertNotEquals(cityBeforeUpdate.getPhoto(), cityAfterUpdate.getPhoto());
    assertNotEquals(cityBeforeUpdate.getName(), cityAfterUpdate.getName());
    assertNotEquals(cityBeforeUpdate.getVersion(), cityAfterUpdate.getVersion());
  }

  @Test
  @WithMockUser(username = "admin", authorities = UserAuthority.FULL_AUTHORITY)
  void testUpdateInternalServerError() throws Exception {
    UpdateCityRequestDto requestDto = new UpdateCityRequestDto("9999", LYON, PHOTO_1);
    mockMvc
        .perform(
            MockMvcRequestBuilders.put(getUrl() + "/{id}", requestDto.getId()).contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
        .andExpect(status().isInternalServerError());

  }

  @Test
  @WithMockUser(username = "user", authorities = UserAuthority.READ_ONLY_AUTHORITY)
  void testUpdateForbidden() throws Exception {
    // Perform the PUT request to the endpoint
    UpdateCityRequestDto requestDto = new UpdateCityRequestDto(ID_1, LYON, "photo");
    mockMvc.perform(
        MockMvcRequestBuilders.put(getUrl() + "/{id}", requestDto.getId()).contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(requestDto)))
        .andExpect(status().isForbidden());
  }

  @Test
  @WithMockUser(username = "user", authorities = UserAuthority.READ_ONLY_AUTHORITY)
  void testGetCitiesAmount() throws Exception {
    // Perform the GET request to the endpoint
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(getUrl() + "/count")).andExpect(status().isOk())
        .andReturn();

    assertEquals("1000", result.getResponse().getContentAsString());
  }
}