package com.example.eBankBackEnd.web;

import com.example.eBankBackEnd.dtos.CustomerDTO;
import com.example.eBankBackEnd.entities.Customer;
import com.example.eBankBackEnd.exception.CustomerNotFoundException;
import com.example.eBankBackEnd.service.IBankAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class CustomerRestControler {
    private IBankAccountService iBankAccountService;

    @GetMapping("/customers")
    public List<CustomerDTO> customers() {
        return iBankAccountService.listCustomers();
    }
    
    @GetMapping("/customers/{id}")
    public  CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        return iBankAccountService.getCustomer(customerId);
    }

    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
       return iBankAccountService.saveCustomer(customerDTO);
    }

    @PutMapping("/customers/{customerid}")
    public CustomerDTO updateCustomer(@PathVariable Long customerid, @RequestBody CustomerDTO customerDTO){
        customerDTO.setId(customerid);
        return iBankAccountService.updateCustomer(customerDTO);
    }

    @DeleteMapping("customers/{id}")
    public void deleteCustomer(@PathVariable Long id){
        iBankAccountService.deleteCustomer(id);
    }
}
