package ro.siit.SpringBootCAE.repositores;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.siit.SpringBootCAE.models.Team;
import ro.siit.SpringBootCAE.models.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query(value="SELECT u FROM Users u WHERE u.username=?1" ,nativeQuery = false)
    User findByUsername(String username);

    @Query(value="SELECT u FROM Users u WHERE u.team=?1" ,nativeQuery = false)
    List<User> findByRole(Team role);

}
