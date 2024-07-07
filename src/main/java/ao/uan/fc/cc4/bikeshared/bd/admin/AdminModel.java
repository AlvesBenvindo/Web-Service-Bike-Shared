package ao.uan.fc.cc4.bikeshared.bd.admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name = "userId")
    protected Long userId;

    
}
