package com.springboot.demo.xml.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.demo.xml.dto.TadigRaexIr21;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProcessXml {

    private Jaxb2Marshaller jaxb2Marshaller;

    public static File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }
        return convFile;
    }

    public ResponseEntity<?> unmarshalXmlUsingFile(MultipartFile file) {
//      Implement the logic to parse and process the XML file or you can use JAXB to unmarshal the XML file into your Java objects

      try {
          /** way 1 starts: to Unmarshal the XML file into Java objects using JAXBContext */
//          Create Unmarshaller from JAXBContext
//          Unmarshaller unmarshaller = JAXBContext.newInstance(TadigRaexIr21.class).createUnmarshaller();

//          Unmarshal the XML file into Java objects
//          TadigRaexIr21 tadigRaexIr21 = (TadigRaexIr21) unmarshaller.unmarshal(file.getInputStream());
          /** end */

          /** way 2 starts: to Unmarshal the XML file into Java objects using Jaxb2Marshaller */
          TadigRaexIr21 tadigRaexIr21 = (TadigRaexIr21) jaxb2Marshaller.unmarshal(new StreamSource(convertMultiPartToFile(file)));
          /** end */

          return new ResponseEntity<>(tadigRaexIr21, HttpStatus.OK);
      } catch (Exception e) {
          e.printStackTrace();
          return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error processing the XML file");
      }
    }

    public ResponseEntity<?> unmarshalXmlUsingStrnig(String xmlData) {
//      Implement the logic to parse and process the XML file or you can use JAXB to unmarshal the XML file into your Java objects

      try {
          /** way 1 starts: to Unmarshal the XML file into Java objects using JAXBContext */
//          Create Unmarshaller from JAXBContext
//          Unmarshaller unmarshaller = JAXBContext.newInstance(TadigRaexIr21.class).createUnmarshaller();

//          Unmarshal the XML file into Java objects
//          TadigRaexIr21 tadigRaexIr21 = (TadigRaexIr21) unmarshaller.unmarshal(new StreamSource(new StringReader(xmlData)));
          /** end */

          /** way 2 starts: to Unmarshal the XML file into Java objects using Jaxb2Marshaller */
//          Unmarshal the XML data into Java objects
          TadigRaexIr21 tadigRaexIr21 = (TadigRaexIr21) jaxb2Marshaller.unmarshal(new StreamSource(new StringReader(xmlData)));
          /** end */

          return new ResponseEntity<>(tadigRaexIr21, HttpStatus.OK);
      } catch (Exception e) {
          e.printStackTrace();
          return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Error processing the XML file");
      }
    }
}
