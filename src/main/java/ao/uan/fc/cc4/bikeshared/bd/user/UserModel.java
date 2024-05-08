package ao.uan.fc.cc4.bikeshared.bd.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class UserModel {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @Column(name = "email")
    protected String email;
    @Column(name = "password")
    protected String password;
    @Column(name = "nome")
    protected String nome;
    @Column(name = "sobrenome")
    protected String sobrenome;
    @Column(name = "genero")
    protected String genero;
    @Column(name = "BI")
    protected String bi;
    @Column(name = "telefone")
    protected String telefone;
    @Column(name = "foto")
    protected String foto;
    @Column(name = "token")
    protected String token;

    /**
     * 
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param String
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * 
     * @return String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     *
     * @param String 
     *
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * 
     * @return String
     */
    public String getNome() {
        return nome;
    }

    /**
     * 
     * @param value
     */
    public void setNome(String value) {
        this.nome = value;
    }

    /**
     * 
     * @return String
     */
    public String getSobrenome() {
        return sobrenome;
    }

    /**
     * Sets the value of the sobrenome property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSobrenome(String value) {
        this.sobrenome = value;
    }

    /**
     * Gets the value of the genero property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getGenero() {
        return genero;
    }

    /**
     * Sets the value of the genero property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setGenero(String value) {
        this.genero = value;
    }

    /**
     * Gets the value of the bi property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getBI() {
        return bi;
    }

    /**
     * Sets the value of the bi property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setBI(String value) {
        this.bi = value;
    }

    /**
     * Gets the value of the telefone property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Sets the value of the telefone property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTelefone(String value) {
        this.telefone = value;
    }

    /**
     * Gets the value of the foto property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getFoto() {
        return foto;
    }

    /**
     * Sets the value of the foto property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setFoto(String value) {
        this.foto = value;
    }

    /**
     * Gets the value of the token property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the value of the token property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setToken(String value) {
        this.token = value;
    }

}
