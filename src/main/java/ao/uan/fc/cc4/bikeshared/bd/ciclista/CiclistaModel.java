package ao.uan.fc.cc4.bikeshared.bd.ciclista;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table( name = "ciclistas" )
@Getter
@Setter
public class CiclistaModel {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column(name = "id")
    private Long id;
    @Column(name = "points")
    protected Integer points;
    @Column(name = "state")
    protected  Integer state;
    @Column(name = "info")
    private String info;
    @Column(name = "userId")
    protected Long userId;

    @Temporal(TemporalType.DATE)
    @Column(name = "createdAt")
    private Timestamp createdAt;


}
