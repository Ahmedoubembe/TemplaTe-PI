package com.authentication.dto;

import com.authentication.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class userResponse {
    private Long id;
    private String email;
    private Role role;
}
