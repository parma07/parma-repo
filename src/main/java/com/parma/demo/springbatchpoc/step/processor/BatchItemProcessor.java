package com.parma.demo.springbatchpoc.step.processor;

import java.util.List;

import org.springframework.batch.item.ItemProcessor;

import com.parma.demo.springbatchpoc.model.BatchTO;

public class BatchItemProcessor implements ItemProcessor<List<BatchTO>, List<BatchTO>>{

	@Override
	public List<BatchTO> process(List<BatchTO> batchTOList) throws Exception {
		System.out.println("List size from Processor:"+batchTOList);
		return batchTOList;
	}

}
