package com.parma.demo.springbatchpoc.step.processor;

import org.springframework.batch.item.ItemProcessor;

public class DemoProcessor implements ItemProcessor<String, String>{

	@Override
	public String process(String str) throws Exception {
		return str.toUpperCase();
	}

	

}
