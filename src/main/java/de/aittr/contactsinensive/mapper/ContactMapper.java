package de.aittr.contactsinensive.mapper;

import de.aittr.contactsinensive.dto.contact.CreateContactDto;
import de.aittr.contactsinensive.dto.contact.ReadContactDto;
import de.aittr.contactsinensive.dto.contact.UpdateContactDto;
import de.aittr.contactsinensive.entity.Contact;
import de.aittr.contactsinensive.service.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper implements EntityMapper<Contact, CreateContactDto, UpdateContactDto, ReadContactDto> {

    @Override
    public Contact formCreateDtoToEntity(CreateContactDto createDto) {
        return new Contact(
                createDto.getFirstName(),
                createDto.getLastName(),
                createDto.getAddress(),
                createDto.getPhone(),
                createDto.getEmail()
        );
    }

    @Override
    public Contact formUpdateToEntity(UpdateContactDto updateDto) {
        return new Contact(
                updateDto.getFirstName(),
                updateDto.getLastName(),
                updateDto.getAddress(),
                updateDto.getPhone(),
                updateDto.getEmail()
        );
    }

    @Override
    public ReadContactDto formEntityToReadDto(Contact entity) {
        return new ReadContactDto(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getAddress(),
                entity.getPhone(),
                entity.getEmail()
        );
    }
}
