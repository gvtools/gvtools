//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.2-hudson-jaxb-ri-2.2-63- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.11.30 at 05:19:07 PM CET 
//


package org.gvsig.persistence.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CompositeLayerType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CompositeLayerType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://gvsigce.org/ExternalCommm}LayerType">
 *       &lt;sequence>
 *         &lt;element name="layers" type="{http://gvsigce.org/ExternalCommm}LayerType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CompositeLayerType", propOrder = {
    "layers"
})
public class CompositeLayerType
    extends LayerType
{

    protected List<LayerType> layers;

    /**
     * Gets the value of the layers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the layers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLayers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LayerType }
     * 
     * 
     */
    public List<LayerType> getLayers() {
        if (layers == null) {
            layers = new ArrayList<LayerType>();
        }
        return this.layers;
    }

}
