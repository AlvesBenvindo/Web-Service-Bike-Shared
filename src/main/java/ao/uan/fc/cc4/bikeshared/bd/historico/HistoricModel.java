package ao.uan.fc.cc4.bikeshared.bd.historico;

import ao.uan.fc.cc4.bikeshared.bd.ciclista.CiclistaModel;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "historics")
@Getter
@Setter
public class HistoricModel {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "ciclistaId")
    protected Long ciclistaId;

    @Column(name = "localName")
    protected String localName;
    @Column(name = "latitude")
    protected Float latitude;
    @Column(name = "longitude")
    protected Float longitude;

    @Column(name = "createdAt")
    private Timestamp createdAt;


}
