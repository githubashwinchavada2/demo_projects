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
public class OrganisationInfo {

    @XmlElement(name = "OrganisationName")
    private String organisationName;

    @XmlElement(name = "CountryInitials")
    private String countryInitials;

    @XmlElement(name = "NetworkList")
    private NetworkList networkList;
}
