package ro.siit.SpringBootCAE.models;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity(name="Requests")
public class Request{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "request_id")
    private UUID id;

    @Column(name ="request_name",nullable = false,  length = 32)
    private String requestName;

    @Column(name ="request_description",nullable = false)
    private String text;


    @ManyToOne
    @JoinColumn(name="request_owner")
    private User owner;


    @OneToMany //(fetch = FetchType.EAGER)??
            (
                    mappedBy ="request",
                    cascade = CascadeType.ALL,
                    orphanRemoval = true
            )

    private List<Response> responseListList;


    public Request(UUID id, String requestName, String text/*User owner*/ ) {
        this.id = id;
        this.requestName = requestName;
        this.text = text;
        /*this.owner = owner*/;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Request(){};


}
