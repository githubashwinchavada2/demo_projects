package com.springboot.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

/** using Jaxb2Marshaller to unmarshal XML data into Java objects */

@Configuration
public class JaxbConfig {

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
//        marshaller.setClassesToBeBound(new Class[]{class1.class, class2.class}); // Set the JAXB annotated classes
        marshaller.setPackagesToScan("com.springboot.demo.xml.dto"); // Set the package where your JAXB annotated classes are located
        return marshaller;
    }
}
