package in.reinventing.cashrich.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.reinventing.cashrich.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
	Optional<User> findByUsernameAndEmail(String username,String email);
	Optional<User> findByUsernameOrEmail(String username, String email);
}
