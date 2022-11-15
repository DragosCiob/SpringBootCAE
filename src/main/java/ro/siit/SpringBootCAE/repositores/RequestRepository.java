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
public interface RequestRepository extends JpaRepository<Request, UUID> {

    @Query(value="SELECT r FROM Requests r WHERE NOT r.owner=?1" ,nativeQuery = false)
    List<Request> sortRequestsByUser(User user);

    @Query(value="SELECT r FROM Requests r WHERE r.owner=?1" ,nativeQuery = false)
    List<Request> findRequestsByUser(User user);

    @Query(value="SELECT r FROM Requests r WHERE r.project=?1" ,nativeQuery = false)
    List<Request> findRequestsByProject(Project project);

    @Query(value="SELECT r FROM Requests r WHERE r.requestName=?1" ,nativeQuery = false)
    Request findRequestByName(String requestName);

    @Query(value="SELECT r FROM Requests r WHERE r.requestName=?1" ,nativeQuery = false)
    List <Request> findRequestsByName(String requestName);

    @Query(value="SELECT r FROM Requests r WHERE r.requestId=?1" ,nativeQuery = false)
    Request findRequestsById(UUID requestId);



}
