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
public class NumberRange {

    @XmlElement(name = "CC")
    private String cc;

    @XmlElement(name = "NDC")
    private String ndc;

    @XmlElement(name = "SN_Range")
    private SNRange snRange;
}
