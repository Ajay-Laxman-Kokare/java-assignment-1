package com.product.javaassignement.controller;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
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

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.product.javaassignement.model.Product;
import com.product.javaassignement.service.service;

@RestController
public class ProductController {
	@Autowired
	service service;
	 private static final String QR_CODE_IMAGE_PATH ="C://Users//ajulk//Downloads//BillDetailsQR.png"; //"./src/main/resources/static/img/QRCode.png";
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
	@GetMapping("/viewBill")
	public ResponseEntity<Map<String, Object>> generateBill(){
		Map<String , Object> billDetails = service.generateBill();
		if(!billDetails.isEmpty()) {
			return ResponseEntity.status(200).body(billDetails);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
	@GetMapping("/generateQR")
	public ResponseEntity<String> generateQR() throws IOException, WriterException{
		
		Map<String , Object> billDetails = service.generateBill();
		if(billDetails !=null) {
			qrCode(billDetails,250,250,QR_CODE_IMAGE_PATH);
			return ResponseEntity.status(200).body("Check your downloads for generated QR");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("System Error Bill QR not generated");
	}
   public void qrCode(Map<String, Object> billdetails, int width, int height, String filePath) throws WriterException, IOException {
	   QRCodeWriter write = new QRCodeWriter();
	   String bill = billdetails.toString();
	   BitMatrix matrix = write.encode(bill, BarcodeFormat.QR_CODE, width, height);
	   Path path = FileSystems.getDefault().getPath(filePath);
	   MatrixToImageWriter.writeToPath(matrix, "PNG", path);
	  
   }
}
