package com.springboot.demo.xml.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.demo.xml.service.ProcessXml;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/xml")
@AllArgsConstructor
public class XmlController {
    
    private ProcessXml processXml;

    @PostMapping("/upload-xml")
    public ResponseEntity<?> handleUploadFile(@RequestParam("file") MultipartFile file) {
        return processXml.unmarshalXmlUsingFile(file);
    }

    @PostMapping("/xmlData")
    public ResponseEntity<?> handleXmlData(@RequestBody String xmlData) {
        return processXml.unmarshalXmlUsingStrnig(xmlData);
    }
}
