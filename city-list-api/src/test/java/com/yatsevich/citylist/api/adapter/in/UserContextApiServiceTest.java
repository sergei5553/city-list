package com.yatsevich.citylist.api.adapter.in;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.yatsevich.citylist.api.adapter.in.service.auth.UserContextApiService;

@WebMvcTest(UserContextApiService.class)
class UserContextApiServiceTest {

  @Autowired
  private MockMvc mockMvc;

  @Value("${cities.version}")
  private String version;

  private String getUrl() {
    return "/api/" + version + "/user/context";
  }

  @Test
  @WithMockUser(username = "user")
  void getContext_withAuthenticatedUser_returnsAuthResponse() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(getUrl()).accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("user"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.authorities").value("ROLE_USER"));
  }

  @Test
  void getContext_withNoUser_returnsUnauthorized() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get(getUrl()).accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

}
