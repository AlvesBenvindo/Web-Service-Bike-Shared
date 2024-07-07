package ao.uan.fc.cc4.bikeshared.bd.message;

import ao.uan.fc.cc4.bikeshared.bd.user.UserModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "messages")
@Getter
@Setter
public class MessageModel {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "message")
    protected String message;

    @Column(name = "emissorId")
    protected Long emissorId;

    @Column(name = "receptorId")
    protected Long receptorId;

    @Temporal(TemporalType.DATE)
    @Column(name = "createdAt")
    private Timestamp createdAt;


}
