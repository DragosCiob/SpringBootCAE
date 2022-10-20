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
    @Column(name = "team")
    @Enumerated(EnumType.STRING)
    private Team team;
//    private String password;


    @OneToMany //(fetch = FetchType.EAGER)??
     (
             mappedBy ="owner",
             cascade = CascadeType.ALL,
             orphanRemoval = true
    )

    private List<Request> requestsList;

    public User(UUID id, String userFirstName, String userSecondName, Team team) {
        this.id = id;
        this.userFirstName = userFirstName;
        this.userSecondName = userSecondName;
        this.team = team;
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
}
