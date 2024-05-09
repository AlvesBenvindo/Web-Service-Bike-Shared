package ao.uan.fc.cc4.bikeshared.bd.user;

import jakarta.persistence.Table;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.io.Serializable;

@Entity
@Table(name = "users")
public class UserModel implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "email", unique = true)
    protected String email;
    @Column(name = "password", nullable = false)
    protected String password;
    @Column(name = "nome", nullable = false)
    protected String nome;
    @Column(name = "sobrenome", nullable = false)
    protected String sobrenome;
    @Column(name = "genero")
    protected String genero;
    @Column(name = "BI")
    protected String bi;
    @Column(name = "telefone")
    protected String telefone;
    @Column(name = "foto")
    protected String foto;
    @Column(name = "tipo", nullable = false)
    protected Integer tipo;

    /**
     *
     * @return
     *     {@link Integer }
     *
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     *     {@link Integer }
     *
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param value
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * 
     * @return
     *     {@link String }
     *
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param value
     *
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * 
     * @return String
     *
     */
    public String getNome() {
        return nome;
    }

    /**
     * 
     * @param value
     *
     */
    public void setNome(String value) {
        this.nome = value;
    }

    /**
     * 
     * @return String
     *
     *
     */
    public String getSobrenome() {
        return sobrenome;
    }

    /**
     *
     * @param value
     *
     */
    public void setSobrenome(String value) {
        this.sobrenome = value;
    }

    /**
     *
     * @return
     *
     */
    public String getGenero() {
        return genero;
    }

    /**
     *
     * @param value
     *
     */
    public void setGenero(String value) {
        this.genero = value;
    }

    /**
     *
     * @return
     *
     */
    public String getBI() {
        return bi;
    }

    /**
     *
     * @param value
     *
     */
    public void setBI(String value) {
        this.bi = value;
    }

    /**
     *
     * @return
     *     {@link String }
     *
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     *
     * @param value
     *     {@link String }
     *
     */
    public void setTelefone(String value) {
        this.telefone = value;
    }

    /**
     *
     * @return
     *     {@link String }
     *
     */
    public String getFoto() {
        return foto;
    }

    /**
     *
     * @param value
     *     {@link String }
     *
     */
    public void setFoto(String value) {
        this.foto = value;
    }

    /**
     *
     * @return
     *     {@link String }
     *
     */
    public Integer getTipo() {
        return tipo;
    }

    /**
     *
     * @param value
     *     {@link String }
     *
     */
    public void setTipo(Integer value) {
        this.tipo = value;
    }

}
