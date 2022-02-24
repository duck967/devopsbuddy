package com.devopsbuddy.backend.persistence.repositories;

import com.devopsbuddy.backend.persistence.domain.backend.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tedonema on 29/03/2016.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

        /**
     * Returns a User given a username or null if not found.
     * @param username The username
     * @return a User given a username or null if not found.
     */
    public User findByUsername(String username);
    
}