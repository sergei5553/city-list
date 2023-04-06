package com.yatsevich.citylist.api.adapter.in.service.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthResponse(
    @Schema(description = "The user name.", example = "user") String name,
    @Schema(description = "The authorities of the user.", example = "READ_ONLY_ACCESS, FULL_ACCESS") String authorities) {
}
