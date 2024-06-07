package ao.uan.fc.cc4.bikeshared.bd.user;

import ao.uan.fc.cc4.bikeshared.utils.Genero;
import ao.uan.fc.cc4.bikeshared.utils.TipoUser;
import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "users")
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
    @Column(name = "bi", unique = true)
    protected String bi;
    @Column(name = "telefone")
    protected String telefone;
    @Column(name = "foto")
    protected String foto;
    @Column(name = "tipo", nullable = false)
    protected int tipo;
    @Temporal(TemporalType.DATE)
    @Column(name = "createdAt")
    private Timestamp createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getBi() {
        return bi;
    }

    public void setBi(String bi) {
        this.bi = bi;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
