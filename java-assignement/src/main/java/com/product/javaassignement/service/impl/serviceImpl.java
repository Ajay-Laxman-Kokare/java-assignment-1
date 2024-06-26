package com.product.javaassignement.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.stereotype.Service;

import com.product.javaassignement.model.Product;
import com.product.javaassignement.service.service;

@Service
public class serviceImpl implements service{
	List<Product> productList = new ArrayList<Product>();
	@Override
	public void addProduct(Product product) {
		if(product !=null) {
			//Product p = new Product();
			productList.add(product);
		}
		
	}
	@Override
	public List<Product> availableProducts() {
		return productList;
	}
	@Override
	public Map<String, Object> generateBill() throws NullPointerException {
		Map<String, Object> billDetails = new HashMap<String, Object>();
//		List<Double> prices = productList.stream().map(p -> p.getPrice()).collect(Collectors.toList());
//		double total = 0;
//		for (Double price : prices) {
//			total += price;
//		}
//		System.out.println("total - "+total);
		try {
			List<Double> totalList = new ArrayList<Double>();
			double total = 0;
			for(Product p : productList) {
				int q = p.getQuantity();
				double price = p.getPrice();
				total = totalCost(q, price);
				totalList.add(total);
				
			}
			double sum = totalList.stream().mapToDouble(Double::valueOf).sum();

			double CGST = calculateTax(total);
			double SGST = calculateTax(total);
			double finalTotal = finalTotal(sum, CGST, SGST);
			billDetails.put("products", productList);
			billDetails.put("total", sum);
			billDetails.put("CGST 9%", CGST);
			billDetails.put("SGST 9%", SGST);
			billDetails.put("final total", finalTotal);
		} catch (Exception e) {
			e.getMessage();
		}
		return billDetails;
	}
	
	double totalCost(int q, double price) {
		double total = q * price;
		return total;
	}
	
	double calculateTax(double total) {
		double tax = total * (0.09); 
		return tax;
	}

	double finalTotal(double sum, double CGST, double SGST) {
		
		return sum + CGST + SGST;
		
	}
}
