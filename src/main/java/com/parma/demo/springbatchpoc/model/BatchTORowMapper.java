package com.parma.demo.springbatchpoc.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class BatchTORowMapper implements RowMapper<BatchTO> {

	@Override
	public BatchTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		BatchTO batchTO = new BatchTO();
		batchTO.setEligibleUnitId(rs.getString("eligibleunitid"));
		batchTO.setEci(rs.getString("eci"));
		batchTO.setCaType(rs.getString("catype"));
		/*batchTO.setLateIndicator(rs.getString("lateindicator"));
		batchTO.setRushIndicator(rs.getString("rushindicator"));
		batchTO.setAmendementIndicator(rs.getString("amendmentindicator"));
		batchTO.setVolantaryMandatory(rs.getString("voluntaryindicator"));
		batchTO.setAccountId(rs.getString("accountid"));
		batchTO.setCadId(rs.getString("cadid"));
		batchTO.setPropagationIndicator(rs.getString("propagationindicator"));*/
		return batchTO;
	}

}
