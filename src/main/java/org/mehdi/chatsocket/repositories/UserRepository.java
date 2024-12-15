package org.mehdi.chatsocket.repositories;

import org.mehdi.chatsocket.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, String> {
    UserDetails findByUsername(String username);

    boolean existsByUsername(String username);
}
