package ao.uan.fc.cc4.bikeshared.bd.session;

import ao.uan.fc.cc4.bikeshared.bd.user.UserModel;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;

@Entity
@Table(name = "sessions")
public class SessionModel {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "token"/*, unique = true, nullable = false*/)
    protected String token;
    @Timestamp

    /**
     *
     * @return Long
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param Long
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return String
     */
    public String getToken() {
        return token;
    }

    /**
     *
     * @param String
     */
    public void setToken(String token) {
        this.token = token;
    }
}
