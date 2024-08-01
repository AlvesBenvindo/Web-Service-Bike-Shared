package ao.uan.fc.cc4.bikeshared.Station3.bd.DataFixed;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "station")
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "endpoint", nullable = false, unique = true)
    protected String endpoint;
    @Column(name = "bonus")
    private int bonus;
    @Column(name = "localName")
    protected String localName;
    @Column(name = "latitude")
    protected Float latitude;
    @Column(name = "longitude")
    protected Float longitude;

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

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
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

    public void setLatitude(double latitude) {
        this.latitude = (float) latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = (float) longitude;
    }
}
