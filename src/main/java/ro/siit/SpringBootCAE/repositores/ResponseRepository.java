package ro.siit.SpringBootCAE.repositores;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.siit.SpringBootCAE.models.Response;

import java.util.UUID;

public interface ResponseRepository extends JpaRepository<Response, UUID>{

}
