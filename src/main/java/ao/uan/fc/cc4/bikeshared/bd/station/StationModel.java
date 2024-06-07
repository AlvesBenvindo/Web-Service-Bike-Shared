package ao.uan.fc.cc4.bikeshared.bd.station;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "stations")
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
    @Column(name = "Docks")
    private Integer Docks;
    @Column(name = "DocksDisp")
    private Integer DocksDisp;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public Integer getDocks() {
        return Docks;
    }

    public void setDocks(Integer docks) {
        Docks = docks;
    }

    public Integer getDocksDisp() {
        return DocksDisp;
    }

    public void setDocksDisp(Integer docksDisp) {
        DocksDisp = docksDisp;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
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
}
