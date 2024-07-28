package ao.uan.fc.cc4.bikeshared.bd.station;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "stations")
@Getter
@Setter
public class StationModel {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "endpoint", nullable = false, unique = true)
    protected String endpoint;
    @Column(name = "bonus")
    private int bonus;
    @Column(name = "qtdDocks")
    private Integer qtdDocks;
    @Column(name = "qtdDocksDispo")
    private Integer qtdDocksDispo;
    @Column(name = "localName")
    protected String localName;
    @Column(name = "latitude")
    protected Float latitude;
    @Column(name = "longitude")
    protected Float longitude;

    @Column(name = "createdAt")
    private Timestamp createdAt;
    
}
