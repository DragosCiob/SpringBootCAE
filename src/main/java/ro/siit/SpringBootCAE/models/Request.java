package ro.siit.SpringBootCAE.models;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity(name="Requests")
@Inheritance(strategy = InheritanceType.JOINED)
public class Request{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "request_id")
    private UUID requestId;

    @Column(name ="request_name",nullable = false,  length = 32)
    private String requestName;

    @Column(name ="request_description",nullable = false)
    private String text;

    @Column(name ="request_index",nullable = false)
    private Double index;

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

    private List<Response> responseList;



    /** relation bidirectional many to one with Project  */
    @ManyToOne
    @JoinColumn(name="project_id")
    private Project project;


    public Request(UUID id, String requestName, String text,Project project /*Integer index*/ ) {
        this.requestId = id;
        this.requestName = requestName;
        this.text = text;
        this.project=project;
        /*this.index=index;*/
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


    public List<Response> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<Response> responseList) {
        this.responseList = responseList;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Double getIndex() {
        return index;
    }

    public void setIndex(Double index) {
        this.index = index;
    }

    public Request(){};


}
