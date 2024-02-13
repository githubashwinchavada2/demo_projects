package com.springboot.demo.batch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.springboot.demo.entity.Employee;
import com.springboot.demo.repository.EmployeeRepository;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DataSource dataSource;

//    To create job
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

//    To create step
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

//  creating reader
    @Bean
    public FlatFileItemReader<Employee> reader() {
        FlatFileItemReader<Employee> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("employees.csv"));
        reader.setLineMapper(getLineMapper());
        reader.setLinesToSkip(1);

        return reader;
    }

    private LineMapper<Employee> getLineMapper() {
        DefaultLineMapper<Employee> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[] {"Emp Id", "First Name", "Gender", "Salary", "Senior Management", "Team"});
        lineTokenizer.setIncludedFields(new int[] {0, 1, 2, 3, 4, 5});

        BeanWrapperFieldSetMapper<Employee> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Employee.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

//  creating processor
    @Bean
    public EmployeeItemProcessor processor() {
        return new EmployeeItemProcessor();
    }

//  creating writer with JDBC
//    @Bean
//    public JdbcBatchItemWriter<Employee> writer() {
//        JdbcBatchItemWriter<Employee> writer = new JdbcBatchItemWriter<>();
//        writer.setDataSource(dataSource);
//        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Employee>());
//        writer.setSql("insert into employee(id, first_name, gender, salary, senior_management, team) "
//                + "values (:empId, :firstName, :gender, :salary, :seniorManagement, :team)");
//
//        return writer;
//    }

//  creating writer with JPA
    @Bean
    public ItemWriter<Employee> writer() {
        return employees -> {
            employeeRepository.saveAll(employees);
        };
    }

//  creating job
    @Bean
    public Job importEmployeeJob() {
        return jobBuilderFactory.get("EMPLOYEE-IMPORT-JOB")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
//                .next(step2())
                .end()
                .build();
    }

//  creating step
    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Employee, Employee>chunk(100) // size of records will be saved in database at a time
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .faultTolerant()
//                .skipLimit(10) // If more than 10 records are not in the proper format, the job fails.
//                .skip(CsvFormatException.class)
                .listener(new EmployeeListener()) // a custom listener is defined to display the skipped records details on the console
                .build();
    }
}
