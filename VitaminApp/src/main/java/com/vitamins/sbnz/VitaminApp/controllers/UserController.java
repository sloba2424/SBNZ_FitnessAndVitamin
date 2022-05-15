package com.sbnz.ibar.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "https://localhost:4200", maxAge = 3600, allowedHeaders = "*")
public class UserController {

//
//	private UserService userService;
//
//
//	private UserMapper userMapper;
//
//	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_REGISTERED_USER')")
//	@PutMapping(value = "/change-profile", consumes = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<?> changeProfile(@Valid @RequestBody UserDTO userDTO) {
//		try {
//			return new ResponseEntity<>(userMapper.toResDTO(userService.changeProfile(userMapper.toEntity(userDTO))),
//					HttpStatus.OK);
//		} catch (NoSuchElementException e) {
//			return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
//		} catch (Exception e) {
//			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//		}
//	}

}
