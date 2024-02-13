package com.springboot.demo.xml.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@XmlRootElement(name = "TADIGRAEXIR21", namespace = "https://infocentre.gsm.org/TADIG-RAEX-IR21")
//@XmlSchema(namespace = "https://infocentre.gsm.org/TADIG-RAEX-IR21",
//    elementFormDefault = XmlNsForm.QUALIFIED,
//    xmlns = {@XmlNs(prefix = "tadig-gen", namespaceURI = "https://infocentre.gsm.org/TADIG-GEN"),
//            @XmlNs(prefix = "tadig-raex-21", namespaceURI = "https://infocentre.gsm.org/TADIG-RAEX-IR21"),
//            @XmlNs(prefix = "xsi", namespaceURI = "http://www.w3.org/2001/XMLSchema-instance")})
@XmlRootElement(name = "TADIGRAEXIR21")
@XmlAccessorType(XmlAccessType.FIELD)
public class TadigRaexIr21 {

   @XmlElement(name = "RAEXIR21FileHeader")
   private RaexIr21FileHeader raexIr21FileHeader;

   @XmlElement(name = "OrganisationInfo")
   private OrganisationInfo organisationInfo;
}
