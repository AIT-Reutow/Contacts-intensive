package de.aittr.contactsinensive.dto.contact;

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
@Schema(description = "Базовый DTO для представления контакта")
public class BaseContactDto implements Serializable {

    @Schema(description = "Имя контакта", example = "Иван")
    protected String firstName;

    @Schema(description = "Фамилия контакта", example = "Иванов")
    protected String lastName;

    @Schema(description = "Адрес контакта", example = "ул. Ленина, д. 1")
    protected String address;

    @Schema(description = "Телефон контакта", example = "+79161234567")
    protected String phone;

    @Schema(description = "Электронная почта контакта", example = "ivanov@mail.com")
    protected String email;

    @Override
    public String toString() {
        return "BaseContactDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
