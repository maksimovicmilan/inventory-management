package com.InventoryManagement.inventorymanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.Arrays;

@SpringBootApplication
@EnableCaching
public class InventoryManagementApplication {


	public static void main(String[] args) {
		SpringApplication.run(InventoryManagementApplication.class, args);


	}

}
