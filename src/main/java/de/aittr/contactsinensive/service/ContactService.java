package de.aittr.contactsinensive.service;


import de.aittr.contactsinensive.dto.contact.CreateContactDto;
import de.aittr.contactsinensive.dto.contact.ReadContactDto;
import de.aittr.contactsinensive.dto.contact.UpdateContactDto;
import de.aittr.contactsinensive.entity.Contact;
import de.aittr.contactsinensive.entity.User;
import de.aittr.contactsinensive.mapper.ContactMapper;
import de.aittr.contactsinensive.persistence.ContactRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactService extends CRUDService<Contact, CreateContactDto, UpdateContactDto, ReadContactDto> {

    private final UserService userService;

    protected ContactService(ContactRepository repository, ContactMapper mapper, UserService userService) {
        super(repository, mapper);
        this.userService = userService;
    }

    @Override
    @Transactional
    public Contact save(Contact entity) {
        User authUser = userService.getAuthUser();
        Contact save = super.save(entity);
        save.setUser(authUser);
        authUser.getContacts().add(save);
        return save;
    }

    @Override
    @Transactional
    public Optional<Contact> findEntityById(long id) {
        User authUser = userService.getAuthUser();
        return ((ContactRepository) repository).findByUserAndId(authUser, id);
    }

//    @Override
//    @Transactional
//    public ReadContactDto update(long id, UpdateContactDto updateDto) {
//        User authUser = userService.getAuthUser();
//        Contact orThrowById = findEntityOrThrowById(id);
//        if (Objects.nonNull(orThrowById.getUser()) && authUser == orThrowById.getUser()) {
//            return super.update(id, updateDto);
//        } else {
//            throw new BadRequestException("Contact not found or not owned by user");
//        }
//    }

    @Transactional
    public List<ReadContactDto> findAll(User authUser) {
        return ((ContactRepository) repository).findAllByUser(authUser).stream()
                .filter(contact -> !contact.equals(authUser.getMyContact()))
                .map(entityMapper::formEntityToReadDto)
                .collect(Collectors.toList());
    }
}
