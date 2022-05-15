package com.sbnz.ibar.controllers;

import com.sbnz.ibar.dto.UserDto;
import com.sbnz.ibar.dto.UserLoginDto;
import com.sbnz.ibar.exceptions.EntityAlreadyExistsException;
import com.sbnz.ibar.exceptions.EntityDoesNotExistException;
import com.sbnz.ibar.model.User;
import com.sbnz.ibar.services.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    private final UserService userDetailsService;

    private final AuthService authService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthTokenDto> login(@Valid @RequestBody UserLoginDto loginDto)
            throws AuthenticationException {
        return ResponseEntity.ok(this.authService.login(loginDto));
    }

    @PostMapping(value = "/sign-up", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addUser(@Valid @RequestBody UserDto user)
            throws EntityAlreadyExistsException, EntityDoesNotExistException {
        UserDto newUser = authService.registerNewUser(user);

        return ResponseEntity.ok(newUser);
    }

    @PostMapping(value = "/forgot-password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> forgotPassword(@RequestBody PasswordReset passwordReset) {
        User user = this.userDetailsService.findByEmail(passwordReset.getEmail());
        if (user == null) {
            return new ResponseEntity<>("User with given email doesn't exist.", HttpStatus.BAD_REQUEST);
        }
        userDetailsService.forgotPassword(passwordReset.getEmail());
        return new ResponseEntity<>("Password reset successfully.", HttpStatus.OK);
    }

    @PostMapping(value = "/change-password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changePassword(@Valid @RequestBody PasswordChanger passwordChanger) {
        try {
            authService.changePassword(passwordChanger.getOldPassword(), passwordChanger.getNewPassword());
        } catch (Exception e) {
            return new ResponseEntity<>("Incorrect old password", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(null);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    static class PasswordReset {
        private String email;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    static class PasswordChanger {
        private String oldPassword;
        private String newPassword;
        private String repeatedPassword;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGISTERED_USER')")
    @GetMapping(value = "/current-user")
    public ResponseEntity<UserDto> currentUser() {
//		User current = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		return new ResponseEntity<>(userMapper.toResDTO(current), HttpStatus.OK);

        return null;
    }

}
