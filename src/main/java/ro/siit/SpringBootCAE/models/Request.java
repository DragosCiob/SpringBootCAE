package ro.siit.SpringBootCAE.models;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity(name="Requests")
public class Request{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "request_id")
    private UUID requestId;

    @Column(name ="request_name",nullable = false,  length = 32)
    private String requestName;

    @Column(name ="request_description",nullable = false)
    private String text;

    /** relation bidirectional many to one with User  */
    @ManyToOne
    @JoinColumn(name="request_owner")
    private User owner;

    /** relation bidirectional one to many with Response  */
    @OneToMany //(fetch = FetchType.EAGER)??
            (
                    mappedBy ="request",
                    cascade = CascadeType.ALL,
                    orphanRemoval = true
            )

    private List<Response> responseListList;



    /** relation bidirectional many to one with Project  */
    @ManyToOne
    @JoinColumn(name="project_id")
    private Project project;




    public Request(UUID id, String requestName, String text/*User owner*/ ) {
        this.requestId = id;
        this.requestName = requestName;
        this.text = text;
        /*this.owner = owner*/;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }

    public String getRequestName() {
        return requestName;
    }

    public void setRequestName(String requestName) {
        this.requestName = requestName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }


    public List<Response> getResponseListList() {
        return responseListList;
    }

    public void setResponseListList(List<Response> responseListList) {
        this.responseListList = responseListList;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }



    public Request(){};


}
