package ao.uan.fc.cc4.bikeshared.bd.ciclista;

import ao.uan.fc.cc4.bikeshared.bd.user.UserModel;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table( name = "ciclistas" )
public class CiclistaModel {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column(name = "id")
    private Long id;
    @Column(name = "points")
    protected Integer points;
    @Column(name = "state")
    protected  Integer state;

    @Column(name = "user")
    protected Long user;

    @Temporal(TemporalType.DATE)
    @Column(name = "createdAt")
    private Timestamp createdAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
