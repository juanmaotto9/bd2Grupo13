package ar.edu.unlp.info.bd2.repositories;

import ar.edu.unlp.info.bd2.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    //Optional<User> findByEmail(String email);
    //Optional<User> findByUsername(String username);
}