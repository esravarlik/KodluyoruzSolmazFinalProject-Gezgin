package com.jojo.gezginservice.request;

import com.jojo.gezginservice.model.enums.Role;
import com.jojo.gezginservice.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private String email;

    private String password;

    private UserType userType;

    private String phoneNumber;

    private Role role;

}
