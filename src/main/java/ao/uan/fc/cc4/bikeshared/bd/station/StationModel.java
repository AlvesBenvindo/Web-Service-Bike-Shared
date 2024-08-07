package ao.uan.fc.cc4.bikeshared.bd.station;

import javax.persistence.*;

import ao.uan.fc.cc4.bikeshared.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// import java.sql.Timestamp;

@Entity
@Table(name = "stations")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    @Column(name = "state", nullable = true)
    private int state;

    @Column(name = "createdAt")
    private String createdAt;

    public String returnString () {
        return this.id +":"+ this.name +":"+this.endpoint+":"+this.bonus+":"+this.qtdDocks+
        ":"+this.qtdDocksDispo +":"+ this.localName +":"+this.latitude+":"+this.longitude+":"+this.state;
    }

    public static StationModel parse (String objectString){
        String [] data = objectString.split(objectString);
        StationModel s = new StationModel();
        s.setId(new Long(data[0]));
        s.setName(data[1]);
        s.setEndpoint(data[2]);
        s.setBonus(Utils.toInteger(data[3]));
        s.setQtdDocks(Utils.toInteger(data[4]));
        s.setQtdDocksDispo(Utils.toInteger(data[5]));
        s.setLocalName(data[6]);
        s.setLatitude(new Float(data[7]));
        s.setLongitude(new Float(data[8]));
        s.setState(Utils.toInteger(data[9]));
        return s;
    }
    
}
