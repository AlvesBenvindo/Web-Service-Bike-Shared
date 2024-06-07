//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.06.06 at 03:38:30 PM WAT 
//


package xml.soap.station;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


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
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Docks" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="DocksDisp" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="localName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="latitude" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
 *         &lt;element name="longitude" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
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
    "name",
    "docks",
    "docksDisp",
    "localName",
    "latitude",
    "longitude"
})
@XmlRootElement(name = "StationResponse")
public class StationResponse {

    protected boolean estado;
    @XmlElement(required = true)
    protected String mensagem;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(name = "Docks")
    protected int docks;
    @XmlElement(name = "DocksDisp")
    protected int docksDisp;
    @XmlElement(required = true)
    protected String localName;
    protected float latitude;
    protected float longitude;

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
     * Gets the value of the docks property.
     * 
     */
    public int getDocks() {
        return docks;
    }

    /**
     * Sets the value of the docks property.
     * 
     */
    public void setDocks(int value) {
        this.docks = value;
    }

    /**
     * Gets the value of the docksDisp property.
     * 
     */
    public int getDocksDisp() {
        return docksDisp;
    }

    /**
     * Sets the value of the docksDisp property.
     * 
     */
    public void setDocksDisp(int value) {
        this.docksDisp = value;
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

}
