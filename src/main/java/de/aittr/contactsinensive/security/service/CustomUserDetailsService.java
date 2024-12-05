package de.aittr.contactsinensive.security.service;

import de.aittr.contactsinensive.entity.User;
import de.aittr.contactsinensive.exception.ResourceNotFoundException;
import de.aittr.contactsinensive.persistence.UserRepository;
import de.aittr.contactsinensive.security.model.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + username));
        return new CustomUserDetails(user);
    }
}
