package de.aittr.contactsinensive.mapper;

import de.aittr.contactsinensive.dto.user.BaseUserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDto extends BaseUserDto {

    private String password;
}
