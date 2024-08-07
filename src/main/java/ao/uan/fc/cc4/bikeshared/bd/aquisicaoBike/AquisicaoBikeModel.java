package ao.uan.fc.cc4.bikeshared.bd.aquisicaoBike;

// import java.sql.Timestamp;
import javax.persistence.*;

import ao.uan.fc.cc4.bikeshared.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "aquisicoes_bike")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AquisicaoBikeModel {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column(name = "id")
    private Long id;
    @Column(name = "station")
    private String Station;
    @Column(name = "tipo_aquisicao")
    private int tipo_aquisicao;
    @Column(name = "ciclistaId")
    private Long ciclistaId;
    @Column(name = "createdAt")
    private String createdAt;

    public String returnString () {
        return this.id +":"+ this.Station +":"+this.tipo_aquisicao+":"+this.ciclistaId+":"+this.createdAt;
    }

    public static AquisicaoBikeModel parse (String objectString){
        String [] data = objectString.split(objectString);
        AquisicaoBikeModel a = new AquisicaoBikeModel();
        a.setId(new Long(data[0]));
        a.setStation(data[1]);
        a.setTipo_aquisicao(Utils.toInteger(data[2]));
        a.setCiclistaId(new Long(data[3]));
        a.setCreatedAt(data[4]);
        return a;
    }


}
