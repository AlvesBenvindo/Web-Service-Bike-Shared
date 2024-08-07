package ao.uan.fc.cc4.bikeshared.bd.admin;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="admins")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminModel {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column(name = "id")
    private Long id;
    @Column(name = "bi", unique = true, nullable = false)
    private String bi;
    @Column(name = "telefone", unique = true, nullable = false)
    private  String telefone;
    @Column(name = "role", nullable = false)
    private  String role;
    @Column(name = "userId")
    private Long userId;

    public String returnString () {
        return this.id +":"+ this.bi +":"+this.telefone+":"+this.role+":"+this.userId;
    }

    public static AdminModel parse (String objectString){
        String [] data = objectString.split(objectString);
        AdminModel a = new AdminModel();
        a.setId(new Long(data[0]));
        a.setBi(data[1]);
        a.setTelefone(data[2]);
        a.setRole(data[3]);
        a.setUserId(new Long(data[4]));
        return a;
    }

}
