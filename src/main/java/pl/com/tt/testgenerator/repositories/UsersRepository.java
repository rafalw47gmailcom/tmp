package pl.com.tt.testgenerator.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.com.tt.testgenerator.entieties.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String name);
}
