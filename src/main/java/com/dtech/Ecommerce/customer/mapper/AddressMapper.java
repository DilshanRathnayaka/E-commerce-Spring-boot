package com.dtech.Ecommerce.customer.mapper;

import com.dtech.Ecommerce.customer.customerDTO.AddressDTO;
import com.dtech.Ecommerce.customer.model.Address;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    @Named("toAddressDTO")
    AddressDTO toAddressDTO(Address address);

    Address toAddress(AddressDTO addressDTO);

    @IterableMapping(qualifiedByName = "toAddressDTO")
    List<AddressDTO> toAddressDTOs(Iterable<Address> addresses);

}
