package com.dtech.Ecommerce.customer.customerController;

import com.dtech.Ecommerce.customer.customerDTO.CustomerDTO;
import com.dtech.Ecommerce.customer.customerService.Impl.CustomerServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerServiceImpl customerService;

    @PostMapping("/create")
    public ResponseEntity<?> saveCustomer(@RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity<>(customerService.saveCustomer(customerDTO), HttpStatus.OK);
    }

    @GetMapping("/customerCount")
    public ResponseEntity<?> getCustomerCount() {
        return new ResponseEntity<>(customerService.getCustomerCount(), HttpStatus.OK);
    }

    @PostMapping("/getCustomer/{email}")
    public ResponseEntity<?> getCustomerByEmail(@PathVariable String email) {
        return new ResponseEntity<>(customerService.getCustomerByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/getCustomerAddressById/{id}")
    public ResponseEntity<?> getCustomerAddresById(@PathVariable Integer id) {
        return new ResponseEntity<>(customerService.getCustomerAddresById(id), HttpStatus.OK);
    }

    @PostMapping("/deleteAddress/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable Integer id) {
        return new ResponseEntity<>(customerService.deleteAddress(id), HttpStatus.OK);
    }
}
