//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.08.07 at 05:44:33 AM WEST 
//


package xml.soap.station;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="estado" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="mensagem" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="stateCode" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="bonus" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="qtdDocks" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="qtdDocksDispo" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="localName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="pais" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="provincia" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="municipio" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="distrito" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="avenida" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="latitude" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
 *         &lt;element name="longitude" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
 *         &lt;element name="dockItem" type="{http://station.soap.xml}DockType" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "estado",
    "mensagem",
    "stateCode",
    "id",
    "name",
    "bonus",
    "qtdDocks",
    "qtdDocksDispo",
    "localName",
    "pais",
    "provincia",
    "municipio",
    "distrito",
    "avenida",
    "latitude",
    "longitude",
    "dockItem"
})
@XmlRootElement(name = "StationResponse")
public class StationResponse {

    protected boolean estado;
    @XmlElement(required = true)
    protected String mensagem;
    protected int stateCode;
    protected long id;
    @XmlElement(required = true)
    protected String name;
    protected int bonus;
    protected int qtdDocks;
    protected int qtdDocksDispo;
    @XmlElement(required = true)
    protected String localName;
    @XmlElement(required = true)
    protected String pais;
    @XmlElement(required = true)
    protected String provincia;
    @XmlElement(required = true)
    protected String municipio;
    @XmlElement(required = true)
    protected String distrito;
    @XmlElement(required = true)
    protected String avenida;
    protected float latitude;
    protected float longitude;
    @XmlElement(required = true)
    protected List<DockType> dockItem;

    /**
     * Gets the value of the estado property.
     * 
     */
    public boolean isEstado() {
        return estado;
    }

    /**
     * Sets the value of the estado property.
     * 
     */
    public void setEstado(boolean value) {
        this.estado = value;
    }

    /**
     * Gets the value of the mensagem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMensagem() {
        return mensagem;
    }

    /**
     * Sets the value of the mensagem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMensagem(String value) {
        this.mensagem = value;
    }

    /**
     * Gets the value of the stateCode property.
     * 
     */
    public int getStateCode() {
        return stateCode;
    }

    /**
     * Sets the value of the stateCode property.
     * 
     */
    public void setStateCode(int value) {
        this.stateCode = value;
    }

    /**
     * Gets the value of the id property.
     * 
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(long value) {
        this.id = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the bonus property.
     * 
     */
    public int getBonus() {
        return bonus;
    }

    /**
     * Sets the value of the bonus property.
     * 
     */
    public void setBonus(int value) {
        this.bonus = value;
    }

    /**
     * Gets the value of the qtdDocks property.
     * 
     */
    public int getQtdDocks() {
        return qtdDocks;
    }

    /**
     * Sets the value of the qtdDocks property.
     * 
     */
    public void setQtdDocks(int value) {
        this.qtdDocks = value;
    }

    /**
     * Gets the value of the qtdDocksDispo property.
     * 
     */
    public int getQtdDocksDispo() {
        return qtdDocksDispo;
    }

    /**
     * Sets the value of the qtdDocksDispo property.
     * 
     */
    public void setQtdDocksDispo(int value) {
        this.qtdDocksDispo = value;
    }

    /**
     * Gets the value of the localName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocalName() {
        return localName;
    }

    /**
     * Sets the value of the localName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocalName(String value) {
        this.localName = value;
    }

    /**
     * Gets the value of the pais property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPais() {
        return pais;
    }

    /**
     * Sets the value of the pais property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPais(String value) {
        this.pais = value;
    }

    /**
     * Gets the value of the provincia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * Sets the value of the provincia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvincia(String value) {
        this.provincia = value;
    }

    /**
     * Gets the value of the municipio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMunicipio() {
        return municipio;
    }

    /**
     * Sets the value of the municipio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMunicipio(String value) {
        this.municipio = value;
    }

    /**
     * Gets the value of the distrito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistrito() {
        return distrito;
    }

    /**
     * Sets the value of the distrito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistrito(String value) {
        this.distrito = value;
    }

    /**
     * Gets the value of the avenida property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAvenida() {
        return avenida;
    }

    /**
     * Sets the value of the avenida property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAvenida(String value) {
        this.avenida = value;
    }

    /**
     * Gets the value of the latitude property.
     * 
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * Sets the value of the latitude property.
     * 
     */
    public void setLatitude(float value) {
        this.latitude = value;
    }

    /**
     * Gets the value of the longitude property.
     * 
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * Sets the value of the longitude property.
     * 
     */
    public void setLongitude(float value) {
        this.longitude = value;
    }

    /**
     * Gets the value of the dockItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dockItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDockItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DockType }
     * 
     * 
     */
    public List<DockType> getDockItem() {
        if (dockItem == null) {
            dockItem = new ArrayList<DockType>();
        }
        return this.dockItem;
    }

}
