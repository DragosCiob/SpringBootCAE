package ro.siit.SpringBootCAE.repositores;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.siit.SpringBootCAE.models.Request;
import ro.siit.SpringBootCAE.models.Response;
import ro.siit.SpringBootCAE.models.User;

import java.util.List;
import java.util.UUID;

public interface ResponseRepository extends JpaRepository<Response, UUID>{

//    @Query(value="SELECT rs FROM Responses rs JOIN Requests r ON r.requestId=rs.requestId WHERE rs.requestId=?1" ,nativeQuery = false)
//    List<Response> findResponsesByRequest(UUID requestId);
//

}
