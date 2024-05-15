package com.InventoryManagement.service;

import com.InventoryManagement.entity.Customer;
import com.InventoryManagement.entity.dto.CustomerDto;
import com.InventoryManagement.exception.ApplicationException;
import com.InventoryManagement.exception.BusinessException;
import com.InventoryManagement.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<CustomerDto> getAllOrders() throws BusinessException {
        try{
            List<CustomerDto> customers = customerRepository.findAll();
            if(customers.isEmpty()){
                throw new BusinessException("You don't have any customers yet, please create a customer first");
            }
            return customerRepository.findAll();
        }catch(ApplicationException e){
            e.fillInStackTrace();
        }
        return null;
    }
}
