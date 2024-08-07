package ao.uan.fc.cc4.bikeshared.bd.ciclista;

import javax.persistence.*;

import ao.uan.fc.cc4.bikeshared.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table( name = "ciclistas" )
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "createdAt")
    private Timestamp createdAt;

    public String returnString () {
        return this.id +":"+ this.points +":"+this.state+":"+this.info+":"+this.userId;
    }

    public static CiclistaModel parse (String objectString){
        String [] data = objectString.split(objectString);
        CiclistaModel a = new CiclistaModel();
        a.setId(new Long(data[0]));
        a.setPoints(Utils.toInteger(data[1]));
        a.setState(Utils.toInteger(data[2]));
        a.setInfo(data[3]);
        a.setUserId(new Long(data[4]));
        return a;
    }


}
