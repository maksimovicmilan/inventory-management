package com.InventoryManagement.service;

import com.InventoryManagement.entity.Customer;
import com.InventoryManagement.entity.dto.CustomerDto;
import com.InventoryManagement.exception.ApplicationException;
import com.InventoryManagement.exception.BusinessException;
import com.InventoryManagement.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<CustomerDto> getAllCustomers() throws BusinessException {
        try{
            List<Customer> customers = customerRepository.findAll();
            if(customers.isEmpty()){
                throw new BusinessException("You don't have any customers yet, please create a customer first");
            }
            List<CustomerDto> customerDto = new ArrayList<>();

            for (int i = 0; i <= customers.size(); i++) {
                CustomerDto c = new CustomerDto();
                c.setEmail(customers.get(i).getEmail());
                c.setDob(customers.get(i).getDob());
                c.setFirstName(customers.get(i).getFirstName());
                c.setLastName(customers.get(i).getLastName());
                customerDto.add(c);
            }

            return customerDto;
        }catch(ApplicationException e){
            e.fillInStackTrace();
        }
        return null;

    }
}
