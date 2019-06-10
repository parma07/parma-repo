package com.parma.demo.springbatchpoc.step.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

import com.parma.demo.springbatchpoc.model.BatchTO;

public class BatchItemWriter implements ItemWriter<List<BatchTO>> {

	@Override
	public void write(List<? extends List<BatchTO>> arg0) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("arg0 size from writer:"+arg0.size());
	}

}
