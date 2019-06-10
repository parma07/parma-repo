package com.parma.demo.springbatchpoc.step.reader;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.parma.demo.springbatchpoc.model.BatchTO;

public class BatchItemReader implements ItemReader<List<BatchTO>>{
	
//	@Autowired
//	private JdbcTemplate jdbcTemplate;
	
	private DataSource dataSource;
	private String sqlQuery;
	private RowMapper<BatchTO> batchRowMapper;
	
	public BatchItemReader(String query,DataSource dataSource, RowMapper<BatchTO> batchRowMapper) {
		this.sqlQuery=query;
		this.batchRowMapper=batchRowMapper;
		this.dataSource = dataSource;
	}

	@Override
	public List<BatchTO> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		//System.out.println("asasasas"+sqlQuery+":"+dataSource+":"+jdbcTemplate);		
		List<BatchTO> results = null;
		boolean read = false;
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		
		jdbcTemplate.setDataSource(this.dataSource);
		System.out.println("jdbcTemplate:"+jdbcTemplate);
		if(!read) {
			results = jdbcTemplate.query(sqlQuery, batchRowMapper);
		}		
		System.out.println("List SIze:"+results.size());
		read=true;
		return results;
	}

}
