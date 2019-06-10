package com.parma.demo.springbatchpoc.config;

import java.util.List;

import javax.batch.runtime.JobExecution;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import com.parma.demo.springbatchpoc.listener.JobCompletionListener;
import com.parma.demo.springbatchpoc.model.BatchTO;
import com.parma.demo.springbatchpoc.model.BatchTORowMapper;
import com.parma.demo.springbatchpoc.step.processor.BatchItemProcessor;
import com.parma.demo.springbatchpoc.step.processor.DemoProcessor;
import com.parma.demo.springbatchpoc.step.reader.BatchItemReader;
import com.parma.demo.springbatchpoc.step.reader.DemoReader;
import com.parma.demo.springbatchpoc.step.writer.BatchItemWriter;
import com.parma.demo.springbatchpoc.step.writer.DemoWriter;

@Configuration
public class BatchConfig {
	
	@Autowired
	JobBuilderFactory jobBuilderFactory;
	
	@Autowired
	StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	private DataSourceConfig dataSourceConfig;
	
	@Autowired
	SimpleJobLauncher simpleJobLauncher;
	
	@Autowired
	private JobCompletionListener listener;
	
	@Autowired
	private DataSource dataSource;
	
	@Scheduled(fixedDelay= 10000)
	public void initiateIntradayBatch1Job() {
		JobParametersBuilder jobParametersBuilder= new JobParametersBuilder();
		jobParametersBuilder.addString("JOBID", String.valueOf(System.currentTimeMillis()));
		JobParameters jobParam = jobParametersBuilder.toJobParameters();
		JobExecution execution = simpleJobLauncher().run(intradayBatch1Job(listener), jobParam);
	}	
	
	@Bean
	public Job intradayBatch1Job(JobCompletionListener listener){
		return jobBuilderFactory.get("intradayBatch1Job")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(orderStep1())
				.next(orderStep2())
				.end()
				.build();
	}
	
	@Bean
	@Primary
	public Step orderStep1(){
		System.out.println("Inside OrderStep 1:"+dataSource);		
		return stepBuilderFactory.get("orderStep1").
				<List<BatchTO>,List<BatchTO>>chunk(1)
				.reader(demoReader())
				.processor(demoProcessor())
				.writer(demoWriter())
				.build();		
	}	

	private ItemWriter<? super List<BatchTO>> demoWriter() {
		return new BatchItemWriter();
	}

	private ItemProcessor<? super List<BatchTO>, ? extends List<BatchTO>> demoProcessor() {
		return new BatchItemProcessor();
	}

	private ItemReader<List<BatchTO>> demoReader() {
		try {
			String sqlQuery = "SELECT eci, eligibleunitid, catype FROM ashadb.batchto";
			return new BatchItemReader(sqlQuery,dataSource, new BatchTORowMapper());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	@Bean
	public Step orderStep2(){
		System.out.println("Inside OrderStep 2");
		return stepBuilderFactory.get("orderStep2").<String,String>chunk(1)
				.reader(new DemoReader())
				.processor(new DemoProcessor())
				.writer(new DemoWriter())
				.build();
	}
	
	/*@Bean
	public Job eodJob(){
		return jobBuilderFactory.get("eodJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener())
				.flow(orderStep1())
				.next(orderStep2())
				.end()
				.build();
	}*/
	
/*	@Primary
	@Bean(name="dataSource")
	public DataSource dataSource() {
		return DataSourceBuilder.create()
				.username(dataSourceConfig.getUserName())
				.password(dataSourceConfig.getPassword())
				.url(dataSourceConfig.getUrl())
				.driverClassName(dataSourceConfig.getDriverClassName())
				.build();				
	}*/
	
	@Bean
	public DataSource DataSource() {
		BasicDataSource dataSorce = new BasicDataSource();
		dataSorce.setDriverClassName(dataSourceConfig.getDriverClassName());
		dataSorce.setUrl(dataSourceConfig.getUrl());
		dataSorce.setUsername(dataSourceConfig.getUserName());
		dataSorce.setPassword(dataSourceConfig.getPassword());
		return dataSource;
	}
	
	@Bean(name="jdbcTemplate")
	public JdbcTemplate jdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
//	
//	@Bean(name="namedParameterJdbcTemplate")
//	public NamedParameterJdbcTemplate namedParameterJdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
//		return new NamedParameterJdbcTemplate(dataSource);
//	}
	
}
