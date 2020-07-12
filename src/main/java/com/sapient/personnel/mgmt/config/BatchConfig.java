package com.sapient.personnel.mgmt.config;

import com.sapient.personnel.mgmt.domain.EmployeeDTO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DataSource dataSource;

    @Value("${employee.data.file}")
    private Resource inputResource;

    @Value("${employee.data.chunk}")
    private Integer chunk;

    @Bean
    public Job readCSVFileJob() {
        return jobBuilderFactory
                .get("readCSVFileJob")
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory
                .get("step")
                .<EmployeeDTO, EmployeeDTO>chunk(chunk)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public ItemProcessor<EmployeeDTO, EmployeeDTO> processor() {
        return new ItemProcessor<EmployeeDTO, EmployeeDTO>() {
            @Override
            public EmployeeDTO process(EmployeeDTO employeeDTO) throws Exception {
                return employeeDTO;
            }
        };
    }

    @Bean
    public FlatFileItemReader<EmployeeDTO> reader() {
        FlatFileItemReader<EmployeeDTO> itemReader = new FlatFileItemReader<>();
        itemReader.setLineMapper(lineMapper());
        itemReader.setLinesToSkip(1);
        itemReader.setResource(inputResource);
        return itemReader;
    }

    @Bean
    public JdbcBatchItemWriter<EmployeeDTO> writer() {
        JdbcBatchItemWriter<EmployeeDTO> itemWriter = new JdbcBatchItemWriter<>();
        itemWriter.setDataSource(dataSource);
        itemWriter.setSql("INSERT INTO EMPLOYEE (EMPLOYEE_ID, NAME, SUPERVISOR_ID, TITLE, BUSINESS_UNIT, PLACE, SALARY, COMPETENCY) " +
                "VALUES ( :id, :name, :supervisor_id, :title, :bu, :place, :salary, :competency)");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return itemWriter;
    }

    @Bean
    public LineMapper<EmployeeDTO> lineMapper() {
        DefaultLineMapper<EmployeeDTO> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        BeanWrapperFieldSetMapper<EmployeeDTO> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        lineTokenizer.setNames("id", "name", "supervisor_id", "title", "bu", "place", "salary", "competency");
        lineTokenizer.setIncludedFields(0, 1, 2, 3, 4, 5, 6, 7);
        fieldSetMapper.setTargetType(EmployeeDTO.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }
}
