package ao.uan.fc.cc4.bikeshared.bd.message;

import ao.uan.fc.cc4.bikeshared.bd.ciclista.CiclistaModel;
import ao.uan.fc.cc4.bikeshared.bd.user.UserModel;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "messages")
public class MessageModel {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "message")
    protected String message;

    @ManyToOne ( cascade = CascadeType.ALL )
    @JoinColumn(name = "id_issuer")
    protected UserModel issuer;

    @ManyToOne ( cascade = CascadeType.ALL )
    @JoinColumn(name = "id_receptor")
    protected UserModel receptor;

    @Temporal(TemporalType.DATE)
    @Column(name = "createdAt")
    private Timestamp createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserModel getIssuer() {
        return issuer;
    }

    public void setIssuer(UserModel issuer) {
        this.issuer = issuer;
    }

    public UserModel getReceptor() {
        return receptor;
    }

    public void setReceptor(UserModel receptor) {
        this.receptor = receptor;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
