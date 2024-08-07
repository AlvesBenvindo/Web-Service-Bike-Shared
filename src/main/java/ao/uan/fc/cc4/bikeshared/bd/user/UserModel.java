package ao.uan.fc.cc4.bikeshared.bd.user;

import javax.persistence.*;

import ao.uan.fc.cc4.bikeshared.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

    public String returnString () {
        return this.id +":"+ this.email +":"+this.password+":"+this.nome+":"+this.sobrenome+
        ":"+this.genero +":"+ this.foto +":"+this.tipo;
    }

    public static UserModel parse (String objectString){
        String [] data = objectString.split(objectString);
        UserModel u = new UserModel();
        u.setId(new Long(data[0]));
        u.setEmail(data[1]);
        u.setPassword(data[2]);
        u.setNome(data[3]);
        u.setSobrenome(data[4]);
        u.setGenero(data[5]);
        u.setFoto(data[6]);
        u.setTipo(Utils.toInteger(data[9]));
        return u;
    }


}
