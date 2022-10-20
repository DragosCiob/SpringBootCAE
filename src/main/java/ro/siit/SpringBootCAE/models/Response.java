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


    @ManyToOne
    @JoinColumn(name="request_id")
    private Request request;

    public Response(UUID id, ResponseType responseType, String comment, Request request) {
        this.id = id;
        this.responseType = responseType;
        this.comment = comment;
        this.request = request;
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
}
