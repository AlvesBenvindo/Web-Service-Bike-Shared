package ao.uan.fc.cc4.bikeshared.bd.session;

import ao.uan.fc.cc4.bikeshared.bd.user.UserModel;
import ao.uan.fc.cc4.bikeshared.config.security.JwtToken.Token;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import xml.soap.user.HeaderType;
import xml.soap.user.LoginRequest;

import java.sql.Timestamp;

@Entity
@Table(name = "sessions")
public class SessionModel {

    private static Token jwtToken = new Token();

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "token", unique = true, nullable = false)
    protected String token;
    @Column(name = "fingerPrint", unique = true)
    protected String fingerPrint;
    @Column(name = "user", unique = true)
    protected Long user;
    @Temporal(TemporalType.DATE)
    @Column(name = "createdAt")
    private Timestamp createdAt;

    public SessionModel () {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public String getFingerPrint() {
        return fingerPrint;
    }

    public void setFingerPrint(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public static int virifySession(SessionModel session) {

        if ( session == null ){
            return  1;
        }
        else if (jwtToken.getSubject(session.getToken()).equals(" ")) {
            return 2;
        }

        return 0;
    }


}
