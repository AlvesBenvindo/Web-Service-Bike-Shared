package ao.uan.fc.cc4.bikeshared.bd.session;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "sessions")
@Getter
@Setter
public class SessionModel {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "token", unique = true, nullable = false)
    protected String token;
    @Column(name = "user", unique = true)
    protected Long user;

    @Column(name = "createdAt")
    private Timestamp createdAt;


}
