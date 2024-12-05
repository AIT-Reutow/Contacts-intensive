package de.aittr.contactsinensive.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO для чтения информации о пользователе")
public class ReadUserDto extends BaseUserDto implements Serializable {

    @Schema(description = "Идентификатор пользователя", example = "1")
    private long id;

    public ReadUserDto(String email, long id) {
        super(email);
        this.id = id;
    }

    @Override
    public String toString() {
        return "ReadUserDto{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}
