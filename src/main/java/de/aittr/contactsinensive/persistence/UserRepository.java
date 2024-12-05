package de.aittr.contactsinensive.persistence;

import de.aittr.contactsinensive.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select (count(u) > 0) from User u where upper(u.email) = upper(:email)")
    boolean isUserExistByEmail(@Param("email") String email);

    Optional<User> findByEmail(@Param("username") String username);
}
