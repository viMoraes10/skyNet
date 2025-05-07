package com.skyNet.dto;

import com.skyNet.Enum.UserRole;

public record RegisterDTO(String username, String password, UserRole role, String email)  {
}

