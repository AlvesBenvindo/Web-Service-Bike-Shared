package ao.uan.fc.cc4.bikeshared.bd.bike;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bikes")
@Getter
@Setter
public class BikeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "marca")
    private String marca;
    @Column(name = "beacon")
    private String beacon;
    @Column(name = "state")
    private Integer state;
}
