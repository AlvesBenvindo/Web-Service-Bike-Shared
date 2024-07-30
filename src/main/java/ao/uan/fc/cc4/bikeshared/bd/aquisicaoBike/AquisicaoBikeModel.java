package ao.uan.fc.cc4.bikeshared.bd.aquisicaoBike;

// import java.sql.Timestamp;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "aquisicoes_bike")
@Getter
@Setter
public class AquisicaoBikeModel {

  @Id
  @GeneratedValue( strategy = GenerationType.AUTO )
  @Column(name = "id")
  private Long id;
  @Column(name = "station")
  private Long Station;
  @Column(name = "tipo_aquisicao")
  private int tipo_aquisicao;
  @Column(name = "ciclistaId")
  private Long ciclistaId;
  @Column(name = "createdAt")
  private String createdAt;


}
