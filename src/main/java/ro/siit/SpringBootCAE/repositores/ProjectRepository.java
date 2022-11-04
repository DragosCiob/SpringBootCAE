package ro.siit.SpringBootCAE.repositores;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.siit.SpringBootCAE.models.Project;

import java.util.UUID;


@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {

}
