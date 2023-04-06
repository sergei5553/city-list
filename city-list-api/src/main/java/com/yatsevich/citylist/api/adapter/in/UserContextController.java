package com.yatsevich.citylist.api.adapter.in;

import com.yatsevich.citylist.api.adapter.in.service.auth.dto.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RequestMapping("/api/${cities.version}/user")
public interface UserContextController {

  @Operation(summary = "Provides details about the user", tags = { "auth" })
  @ApiResponse(responseCode = "200", description = "OK")
  @GetMapping("/context")
  ResponseEntity<AuthResponse> getContext();

}
