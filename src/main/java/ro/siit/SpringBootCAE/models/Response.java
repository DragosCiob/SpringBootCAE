package ro.siit.SpringBootCAE.models;

import javax.persistence.*;
import java.util.UUID;

@Entity(name="Responses")
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "response_id")
    private UUID id;

    @Column(name ="response_type")
    @Enumerated(EnumType.STRING)
    private ResponseType responseType;

    @Column(name ="response_comment", nullable = false)
    private String comment;

    /** relation bidirectional many to one with Request */
    @ManyToOne
    @JoinColumn(name="request_id")
    private Request request;

    /** relation bidirectional many to one with User  */
    @ManyToOne
    @JoinColumn(name="response_owner")
    private User user;


    public Response(UUID id, ResponseType responseType, String comment, Request request /* User user */ ) {
        this.id = id;
        this.responseType = responseType;
        this.comment = comment;
        this.request = request;
        /*this.user = user;*/
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Response(){};

}
