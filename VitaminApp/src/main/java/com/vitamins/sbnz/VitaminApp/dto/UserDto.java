package com.sbnz.ibar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sbnz.ibar.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto {

    private UUID id;

    @NotEmpty(message = "Email cannot be null or empty.")
    @Email(message = "Email format is not valid.")
    private String email;

    @NotEmpty(message = "First name cannot be null or empty.")
    private String firstName;

    @NotEmpty(message = "Last name cannot be null or empty.")
    private String lastName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String password;

    private long age;

    private boolean male;

    private boolean enabled;

    private List<Authority> authorities;

    @NotNull
    private UserType userType;

}
