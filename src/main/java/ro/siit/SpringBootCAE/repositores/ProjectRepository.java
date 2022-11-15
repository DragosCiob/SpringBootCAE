package ro.siit.SpringBootCAE.repositores;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.siit.SpringBootCAE.models.Project;
import ro.siit.SpringBootCAE.models.Request;
import ro.siit.SpringBootCAE.models.User;

import java.util.List;
import java.util.UUID;


@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    @Query(value="SELECT p FROM Projects p WHERE  p.projectMembers=?1" ,nativeQuery = false)
    List<Project> findProjectByUser(User user);
}
