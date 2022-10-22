package ro.siit.SpringBootCAE.repositores;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.siit.SpringBootCAE.models.User;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query(value="SELECT u FROM users u WHERE u.username" ,nativeQuery = true)
    User findByUsername(String username);

}
