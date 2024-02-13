package com.springboot.demo.xml.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
public class RoutingInfo {

    @XmlElement(name = "CCITT_E164_NumberSeries")
    private CCITT_E164_NumberSeries ccittE164NumberSeries;

    @XmlElement(name = "CCITT_E212_NumberSeries")
    private CCITT_E212_NumberSeries ccittE212NumberSeries;

    @XmlElement(name = "CCITT_E214_MGT")
    private CCITT_E214_MGT ccittE214MGT;

    @XmlElement(name = "DoesNumberPortabilityApply")
    private boolean doesNumberPortabilityApply;

    @XmlElement(name = "Is15DigitMSISDNSupported")
    private boolean is15DigitMSISDNSupported;
}
