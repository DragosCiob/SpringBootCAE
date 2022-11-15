package ro.siit.SpringBootCAE.repositores;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.siit.SpringBootCAE.models.ProjectTask;
import ro.siit.SpringBootCAE.models.Request;

import java.util.List;
import java.util.UUID;

public interface ProjectTaskRepository extends JpaRepository<ProjectTask, UUID> {



//SELECT project_name, username, team FROM project_task JOIN requests ON requests.request_id = project_task.request_id
//	JOIN projects ON projects.project_id = requests.project_id
//	JOIN project_user_relation ON project_user_relation.project_id = projects.project_id
//	JOIN users ON users.user_id = project_user_relation.user_id;

}