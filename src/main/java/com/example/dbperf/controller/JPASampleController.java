package com.example.dbperf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dbperf.service.JPASampleService;

@RestController
@RequestMapping("/jpa")
public class JPASampleController {
	
	@Autowired
	private JPASampleService service;
	
	@PostMapping("/batch/{count}")
	public int insertDataByJPABatch(@PathVariable(name = "count", required = true) Integer count) {
		return service.createSampleDataByJPABatch(count);
	}
	
	@PostMapping("/batch-persist/{count}")
	public int insertDataByPersist(@PathVariable(name = "count", required = true) Integer count) {
		return service.createSampleDataByPersistBatch(count);
	}
	
	@PostMapping("/batch-native-query/{count}")
	public int insertDataByNativeQuery(@PathVariable(name = "count", required = true) Integer count) {
		return service.createSampleDataByNativeQuery(count);
	}
}
