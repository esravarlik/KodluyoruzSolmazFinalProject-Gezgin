package com.jojo.gezginservice.response;

import com.jojo.gezginservice.model.enums.Role;
import com.jojo.gezginservice.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserResponse {


    private Integer id;

    private String email;

    private Role role;

    private UserType userType;

    private String phoneNumber;
}
