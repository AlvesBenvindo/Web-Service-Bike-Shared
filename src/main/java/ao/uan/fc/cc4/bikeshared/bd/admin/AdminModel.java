package ao.uan.fc.cc4.bikeshared.bd.admin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="admins")
@Getter
@Setter
public class AdminModel {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column(name = "id")
    private Long id;
    @Column(name = "bi", unique = true, nullable = false)
    protected String bi;
    @Column(name = "telefone", unique = true, nullable = false)
    protected  String telefone;
    @Column(name = "papel", unique = true, nullable = false)
    protected  String papel;
    @Column(name = "userId")
    protected Long userId;

}
