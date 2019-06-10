package com.parma.demo.springbatchpoc.step.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

public class DemoWriter implements ItemWriter<String>{

	@Override
	public void write(List<? extends String> countings) throws Exception {
		for(String count : countings){
			System.out.println(count);
		}
	}

}
