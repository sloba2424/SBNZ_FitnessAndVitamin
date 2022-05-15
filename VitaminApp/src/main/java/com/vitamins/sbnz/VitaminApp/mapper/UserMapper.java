package com.sbnz.ibar.mapper;

import com.sbnz.ibar.dto.UserDto;
import com.sbnz.ibar.model.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    private final AuthorityRepository authortyRepository;

    public UserMapper(AuthorityRepository authortyRepository) {
        this.authortyRepository = authortyRepository;
    }

    public User toEntity(UserDto dto) {
        User entity;
        switch (dto.getUserType()) {
            case ADMIN:
                entity = new Admin();
                break;
            case READER:
                entity = new Reader();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dto.getUserType());
        }

        entity.setEmail(dto.getEmail());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setPassword(dto.getPassword());
        entity.setEnabled(dto.isEnabled());
        entity.setAuthorities(dto.getAuthorities().stream()
                .map(au -> authortyRepository.findById(au.getId()).orElse(new Authority(au.getId(), au.getName())))
                .collect(Collectors.toList()));
        return entity;
    }

    public UserDto toUserDto(User user) {
        UserDto userDto = new UserDto();

        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());

        return userDto;
    }
}
