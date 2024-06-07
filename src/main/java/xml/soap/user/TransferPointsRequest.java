//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.06.06 at 10:45:13 PM WAT 
//


package xml.soap.user;

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
 *         &lt;element name="Header" type="{http://user.soap.xml}HeaderType"/&gt;
 *         &lt;element name="Body"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="dadorId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *                   &lt;element name="BeneficiarioId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *                   &lt;element name="points" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
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
    "header",
    "body"
})
@XmlRootElement(name = "TransferPointsRequest")
public class TransferPointsRequest {

    @XmlElement(name = "Header", required = true)
    protected HeaderType header;
    @XmlElement(name = "Body", required = true)
    protected TransferPointsRequest.Body body;

    /**
     * Gets the value of the header property.
     * 
     * @return
     *     possible object is
     *     {@link HeaderType }
     *     
     */
    public HeaderType getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeaderType }
     *     
     */
    public void setHeader(HeaderType value) {
        this.header = value;
    }

    /**
     * Gets the value of the body property.
     * 
     * @return
     *     possible object is
     *     {@link TransferPointsRequest.Body }
     *     
     */
    public TransferPointsRequest.Body getBody() {
        return body;
    }

    /**
     * Sets the value of the body property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransferPointsRequest.Body }
     *     
     */
    public void setBody(TransferPointsRequest.Body value) {
        this.body = value;
    }


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
     *         &lt;element name="dadorId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
     *         &lt;element name="BeneficiarioId" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
     *         &lt;element name="points" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
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
        "dadorId",
        "beneficiarioId",
        "points"
    })
    public static class Body {

        protected long dadorId;
        @XmlElement(name = "BeneficiarioId")
        protected long beneficiarioId;
        protected long points;

        /**
         * Gets the value of the dadorId property.
         * 
         */
        public long getDadorId() {
            return dadorId;
        }

        /**
         * Sets the value of the dadorId property.
         * 
         */
        public void setDadorId(long value) {
            this.dadorId = value;
        }

        /**
         * Gets the value of the beneficiarioId property.
         * 
         */
        public long getBeneficiarioId() {
            return beneficiarioId;
        }

        /**
         * Sets the value of the beneficiarioId property.
         * 
         */
        public void setBeneficiarioId(long value) {
            this.beneficiarioId = value;
        }

        /**
         * Gets the value of the points property.
         * 
         */
        public long getPoints() {
            return points;
        }

        /**
         * Sets the value of the points property.
         * 
         */
        public void setPoints(long value) {
            this.points = value;
        }

    }

}