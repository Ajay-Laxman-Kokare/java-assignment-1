package com.product.javaassignement.controller;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.product.javaassignement.model.Product;
import com.product.javaassignement.service.service;

@RestController
public class ProductController {
	@Autowired
	service service;
	
	@PostMapping("/addProduct")
	public ResponseEntity<String> addProduct(@RequestBody Product product) {
		if(product != null) {
			//System.out.println("Call happened..");
			Random random = new Random();
			int id = random.nextInt();
			product.setProductId(id);
			service.addProduct(product);
			
			return ResponseEntity.status(200).body("Product added successfully");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please add record properly");
		
	}
	@GetMapping("/getProducts")
	public ResponseEntity<List<Product>> getProducts(){
		List<Product> availableProducts = service.availableProducts();
		if(availableProducts !=null) {
			return ResponseEntity.status(200).body(availableProducts);
		}
		return (ResponseEntity<List<Product>>) ResponseEntity.notFound();
		
	}
	@GetMapping("/generateBill")
	public ResponseEntity<Map<String, Object>> generateBill(){
		Map<String , Object> billDetails = service.generateBill();
		if(!billDetails.isEmpty()) {
			return ResponseEntity.status(200).body(billDetails);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}

}
