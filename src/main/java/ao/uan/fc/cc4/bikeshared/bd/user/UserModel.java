package ao.uan.fc.cc4.bikeshared.bd.user;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserModel implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "email", unique = true, nullable = false)
    protected String email;
    @Column(name = "password", nullable = true)
    protected String password;
    @Column(name = "nome")
    protected String nome;
    @Column(name = "sobrenome")
    protected String sobrenome;
    @Column(name = "genero")
    protected String genero;
    @Column(name = "foto")
    protected String foto;
    @Column(name = "tipo", nullable = false)
    protected int tipo;


}
