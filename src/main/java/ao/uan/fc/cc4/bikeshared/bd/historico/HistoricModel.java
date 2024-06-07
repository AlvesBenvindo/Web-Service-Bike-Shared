package ao.uan.fc.cc4.bikeshared.bd.historico;

import ao.uan.fc.cc4.bikeshared.bd.ciclista.CiclistaModel;
import ao.uan.fc.cc4.bikeshared.bd.user.UserModel;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "historics")
public class HistoricModel {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne ( cascade = CascadeType.ALL )
    @JoinColumn(name = "ciclista_id")
    protected CiclistaModel ciclista;

    @Column(name = "localName")
    protected String localName;
    @Column(name = "latitude")
    protected Float latitude;
    @Column(name = "longitude")
    protected Float longitude;

    @Temporal(TemporalType.DATE)
    @Column(name = "createdAt")
    private Timestamp createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public CiclistaModel getCiclista() {
        return ciclista;
    }

    public void setCiclista(CiclistaModel ciclista) {
        this.ciclista = ciclista;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
