package de.aittr.contactsinensive.mapper;

import de.aittr.contactsinensive.dto.user.BaseUserDto;
import de.aittr.contactsinensive.dto.user.ReadUserDto;
import de.aittr.contactsinensive.entity.User;
import de.aittr.contactsinensive.service.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements EntityMapper<User, CreateUserDto, BaseUserDto, ReadUserDto> {

    @Override
    public User formCreateDtoToEntity(CreateUserDto createDto) {
        return new User(createDto.getEmail(), createDto.getPassword());
    }

    @Override
    public User formUpdateToEntity(BaseUserDto updateDto) {
        return new User(updateDto.getEmail());
    }

    @Override
    public ReadUserDto formEntityToReadDto(User entity) {
        return new ReadUserDto(entity.getEmail(), entity.getId());
    }
}
