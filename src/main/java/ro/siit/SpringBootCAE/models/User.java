package ro.siit.SpringBootCAE.models;


import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity(name="Users")

public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private UUID id;

    @Column(name= "first_name", nullable = false,  length = 32)
    private String userFirstName;
    @Column(name= "second_name",nullable = false,  length = 32) //unique = true,
    private String userSecondName;

    @Column(name="username", nullable = false,  length = 32)
    private String username;

    @Column(name = "team")
    @Enumerated(EnumType.STRING)
    private Team team;

    @Column(name="password",nullable = false,  length = 64)
    private String password;


    @OneToMany //(fetch = FetchType.EAGER)??
     (
             mappedBy ="owner",
             cascade = CascadeType.ALL,
             orphanRemoval = true
    )

    private List<Request> requestsList;

    @OneToMany //(fetch = FetchType.EAGER)??
            (
                    mappedBy ="user",
                    cascade = CascadeType.ALL,
                    orphanRemoval = true
            )

    private List<Response> responsesList;



    public User(UUID id, String userFirstName, String userSecondName, String username, Team team, String password) {
        this.id = id;
        this.userFirstName = userFirstName;
        this.userSecondName = userSecondName;
        this.username =username;
        this.team = team;
        this.password= password;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserSecondName() {
        return userSecondName;
    }

    public void setUserSecondName(String userSecondName) {
        this.userSecondName = userSecondName;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Request> getRequestsList() {
        return requestsList;
    }

    public void setRequestsList(List<Request> requestsList) {
        this.requestsList = requestsList;
    }

    public List<Response> getResponsesList() {
        return responsesList;
    }

    public void setResponsesList(List<Response> responsesList) {
        this.responsesList = responsesList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User(){};
}
