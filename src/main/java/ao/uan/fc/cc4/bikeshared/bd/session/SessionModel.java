package ao.uan.fc.cc4.bikeshared.bd.session;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "sessions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SessionModel {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "token", unique = true, nullable = false)
    protected String token;
    @Column(name = "user", unique = true)
    protected Long user;
    @Column(name = "fingerprint", unique = true)
    protected String fingerprint;

    @Column(name = "createdAt")
    private Timestamp createdAt;


    public String returnString () {
        return this.id +":"+ this.token +":"+this.user+":"+this.fingerprint;
    }

    public static SessionModel parse (String objectString){
        String [] data = objectString.split(objectString);
        SessionModel s = new SessionModel();
        s.setId(new Long(data[0]));
        s.setToken(data[1]);
        s.setUser(new  Long(data[2]));
        s.setFingerprint(data[3]);
        return s;
    }


}
