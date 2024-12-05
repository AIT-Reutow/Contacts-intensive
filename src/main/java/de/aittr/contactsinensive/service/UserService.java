package de.aittr.contactsinensive.service;


import de.aittr.contactsinensive.dto.user.BaseUserDto;
import de.aittr.contactsinensive.dto.user.ReadUserDto;
import de.aittr.contactsinensive.entity.User;
import de.aittr.contactsinensive.exception.ApiException;
import de.aittr.contactsinensive.mapper.CreateUserDto;
import de.aittr.contactsinensive.mapper.UserMapper;
import de.aittr.contactsinensive.persistence.UserRepository;
import de.aittr.contactsinensive.security.model.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService extends CRUDService<User, CreateUserDto, BaseUserDto, ReadUserDto> {

    protected UserService(UserRepository repository, UserMapper mapper) {
        super(repository, mapper);
    }

    public User getAuthUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).user();
        } else {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
    }
}
