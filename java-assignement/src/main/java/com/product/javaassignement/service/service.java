package com.product.javaassignement.service;

import java.util.List;
import java.util.Map;

import com.product.javaassignement.model.Product;

public interface service {
	public void addProduct(Product product);
	List<Product>availableProducts();
	Map<String, Object> generateBill();
}
