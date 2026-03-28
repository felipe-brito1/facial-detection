package com.felipebrito.facial_detection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FacialDetectionApplication {

	public static void main(String[] args) {
		System.setProperty("java.awt.headless", "false");
		System.load("C:\\opencv\\build\\java\\x64\\opencv_java4120.dll");
		SpringApplication.run(FacialDetectionApplication.class, args);

	}

}
