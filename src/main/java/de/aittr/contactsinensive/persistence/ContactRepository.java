package de.aittr.contactsinensive.persistence;

import de.aittr.contactsinensive.entity.Contact;
import de.aittr.contactsinensive.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query("select c from Contact c where c.user = :user and c.id = :contactId")
    Optional<Contact> findByUserAndId(@Param("user") User user, @Param("contactId") long id);


    List<Contact> findAllByUser(@Param("user") User user);
}
