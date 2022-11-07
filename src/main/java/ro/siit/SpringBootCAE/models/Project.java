package ro.siit.SpringBootCAE.models;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity(name="Projects")
public class Project{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_id")
    private UUID projectId;

    @Column(name ="project_name",nullable = false,  length = 64)
    private String projectName;


    /** relation bidirectional one to many with Request */
    @OneToMany //(fetch = FetchType.EAGER)??
            (
                    mappedBy ="project",
                    cascade = CascadeType.ALL,
                    orphanRemoval = true
            )

    private List<Request> projectRequestsList;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "project_user_relation",
            joinColumns = @JoinColumn(name="project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> projectMembers;


    public Project(UUID projectId, String projectName /*Set<User> projectMembers*/) {
        this.projectId = projectId;
        this.projectName = projectName;
        /*this.projectMembers = projectMembers;*/
    }

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }


    public List<Request> getProjectRequestsList() {
        return projectRequestsList;
    }

    public void setProjectRequestsList(List<Request> projectRequestsList) {
        this.projectRequestsList = projectRequestsList;
    }

    public Set<User> getProjectMembers() {
        return projectMembers;
    }

    public void setProjectMembers(Set<User> projectMembers) {
        this.projectMembers = projectMembers;
    }

    public Project(){};
}
