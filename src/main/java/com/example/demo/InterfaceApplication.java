package com.example.demo;

import com.example.demo.async.Async;
import com.example.demo.functional.FunctionalStream;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InterfaceApplication {


	public static void main(String[] args) throws InterruptedException {

		FunctionalStream.run();
		Async.run();

	}



}
