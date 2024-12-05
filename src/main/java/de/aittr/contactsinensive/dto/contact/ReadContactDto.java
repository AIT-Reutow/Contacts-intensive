package de.aittr.contactsinensive.dto.contact;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "DTO для чтения информации о контакте")
public class ReadContactDto extends BaseContactDto implements Serializable {

    @Schema(description = "Идентификатор контакта", example = "1")
    private long id;

    public ReadContactDto(long id,
                          String firstName,
                          String lastName,
                          String address,
                          String phone,
                          String email) {
        super(firstName, lastName, address, phone, email);
        this.id = id;
    }

    @Override
    public String toString() {
        return "ReadContactDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
