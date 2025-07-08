package com.dtech.Ecommerce.customer.customerService;

import com.dtech.Ecommerce.auth.authModel.User;
import com.dtech.Ecommerce.auth.authRepo.UserRepo;
import com.dtech.Ecommerce.auth.mapper.UserMapper;
import com.dtech.Ecommerce.cart.dto.CartDTO;
import com.dtech.Ecommerce.cart.model.Cart;
import com.dtech.Ecommerce.cart.repository.CartRepo;
import com.dtech.Ecommerce.cart.service.CartService;
import com.dtech.Ecommerce.customer.customerDTO.AddressDTO;
import com.dtech.Ecommerce.customer.customerDTO.CustomerDTO;
import com.dtech.Ecommerce.customer.customerService.Impl.CustomerServiceImpl;
import com.dtech.Ecommerce.customer.mapper.AddressMapper;
import com.dtech.Ecommerce.customer.mapper.CountryRepo;
import com.dtech.Ecommerce.customer.mapper.CustomerMapper;
import com.dtech.Ecommerce.customer.model.Address;
import com.dtech.Ecommerce.customer.model.Country;
import com.dtech.Ecommerce.customer.model.Customer;
import com.dtech.Ecommerce.customer.repository.AddressRepo;
import com.dtech.Ecommerce.customer.repository.CustomerRepo;
import com.dtech.Ecommerce.exeption.CustomExeption;
import com.dtech.Ecommerce.fileService.dto.CommonResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService implements CustomerServiceImpl {

    private final CustomerRepo customerRepo;
    private final CustomerMapper customerMapper;
    private final UserRepo userRepo;
    private final CountryRepo countryRepo;
    private final AddressMapper addressMapper;
    private final AddressRepo addressRepo;
    private final CartService cartService;


    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        try {
            User user = userRepo.findByUsername(customerDTO.getUser().getUsername());
            if (user == null) {
                throw new CustomExeption("User not found");
            }

            Customer customer = customerMapper.toCustomer(customerDTO);

            if(customerDTO.getAddress() != null) {
                customer.getUser().setPassword(user.getPassword());

                for(AddressDTO addressDTO : customerDTO.getAddress()) {
                    Optional<Country> country = countryRepo.findCountryByCode(addressDTO.getCountryId().getCode());
                    if (country.isEmpty()) {
                        throw new CustomExeption("Country not found");
                    }
                    List<Address> addressList = addressRepo.findAddressesByCustomer_IdAndStatus(customer.getId(), "Active");
                    if(addressList.size() > 5){
                        throw new CustomExeption("Can have only 5 addresses");
                    }

                    for(Address address : customer.getAddress()) {
                        address.setCountryId(country.get());
                        address.setStatus("Active");
                        if(addressList.isEmpty()){
                            address.setType("Primary");
                        }
                        address.setCustomer(customer);
                    }
                }
            }else{
                customer.setUser(user);
            }

            Customer customer1 = customerRepo.save(customer);
            if(customer1.getId() != null){
                customer.setCart(cartService.newCartId(customer1.getId()));
            }
            return customerMapper.toCustomerDTO(customer1);
        } catch (CustomExeption e) {
            throw new CustomExeption(e.getMessage());
        }
    }

    @Override
    public CommonResponse getCustomerCount() {
        CommonResponse commonResponse = new CommonResponse();
        long count = customerRepo.count();
        commonResponse.setMessage(String.valueOf(count));
        return commonResponse;
    }

    @Override
    public CustomerDTO getCustomerByEmail(String email) {
        Customer customer = customerRepo.findCustomerByEmail(email);
        if (customer == null) {
            throw new CustomExeption("Customer not found");
        }
        List<Address> activeAddresses = customer.getAddress().stream()
                .filter(address -> address.getStatus().equals("Active"))
                .collect(Collectors.toList());
        customer.setAddress(activeAddresses);

        CustomerDTO customerDTO = customerMapper.toCustomerDTO(customer);
        customerDTO.getUser().setPassword(null);
        return customerDTO;
    }

    @Override
    public List<AddressDTO> getCustomerAddresById(Integer id) {
        List<Address> address = addressRepo.findAddressesByCustomer_IdAndStatus(id, "Active");
        if (address == null) {
            throw new CustomExeption("Address not found");
        }
        return addressMapper.toAddressDTOs(address);
    }

    @Override
    public CommonResponse deleteAddress(Integer id) {
        try{
            CommonResponse commonResponse = new CommonResponse();
            Optional<Address> address = addressRepo.findById(id);
            if(address.isEmpty()){
                throw new CustomExeption("Address not found");
            }
            address.get().setStatus("Deleted");
            address.get().setType("secondary");
            addressRepo.save(address.get());
            commonResponse.setMessage("Address deleted");
            return commonResponse;
        }catch (Exception e){
            throw new CustomExeption("Address not found");
        }

    }

}
