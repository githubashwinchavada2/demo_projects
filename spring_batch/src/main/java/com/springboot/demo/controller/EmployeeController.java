package com.springboot.demo.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.demo.entity.Employee;
import com.springboot.demo.service.EmployeeService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/employee")
@AllArgsConstructor
public class EmployeeController {

    public final EmployeeService EmployeeService;

    @Autowired
    ResourceLoader resourceLoader;

//    http://localhost:8080/employee

    @PostMapping("/upload-csv")
    public ResponseEntity<?> handleUploadFile(@RequestParam("file") MultipartFile file) throws IOException {
//        Save the file to a temporary location or process it directly

        try {
//            String tempDirectory = System.getProperty("java.io.tmpdir");
//            System.out.println(tempDirectory);

            String path = new ClassPathResource("tempFolder/").getURL().getPath();
            String fileName = "";
            if (file.getOriginalFilename().contains(".csv")) {
                fileName = file.getOriginalFilename().replaceAll(".csv", "") + "_" + System.currentTimeMillis() + ".csv";
            } else {
                fileName = file.getOriginalFilename() + "_" + System.currentTimeMillis();
            }
            File fileToImport = new File(path + fileName);
            Path filePath = Path.of(fileToImport.getAbsolutePath());
//            Files.copy(file.getInputStream(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
            file.transferTo(filePath.toFile());

            Files.deleteIfExists(filePath);

            return ResponseEntity.ok("CSV File uploaded successfully. File path: " + filePath.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error uploading the CSV file.");
        }
    }

    @PostMapping(value = "/add-employee", consumes = "application/json")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        Employee addedEmployee = EmployeeService.addEmployee(employee);
        return ResponseEntity.ok(addedEmployee);
    }

    @GetMapping(value = "/get-employee", produces = "application/json")
    public ResponseEntity<Employee> getEmployee(@RequestParam("emailId") String emailId) {
        Employee fetchedEmployee = EmployeeService.getEmployee(emailId);
        return ResponseEntity.ok(fetchedEmployee);
    }

    @GetMapping(value = "/get-all-employees", produces = "application/json")
    public ResponseEntity<List<Employee>> getEmployee() {
        List<Employee> fetchedEmployees = EmployeeService.getEmployee();
        return ResponseEntity.ok(fetchedEmployees);
    }
}
