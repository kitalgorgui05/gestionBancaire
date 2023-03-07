package com.gestion.banking.services.impl;

import com.gestion.banking.dto.AddressDto;
import com.gestion.banking.repositories.AddressRepository;
import com.gestion.banking.services.AddressService;
import com.gestion.banking.validators.ObjectsValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final ObjectsValidator<AddressDto> validator;

    public AddressServiceImpl(AddressRepository addressRepository, ObjectsValidator<AddressDto> validator) {
        this.addressRepository = addressRepository;
        this.validator = validator;
    }

    @Override
    @Transactional
    public String save(AddressDto dto) {
        validator.validate(dto);
        return addressRepository.saveAndFlush(AddressDto.toEntity(dto)).getId();
    }

    @Override
    public List<AddressDto> findAll() {
        return addressRepository.findAll()
                .stream()
                .map(AddressDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AddressDto findById(String id) {
        return addressRepository.findById(id)
                .map(AddressDto::toDto)
                .orElseThrow(()-> new EntityNotFoundException("No address found with the id :" +id));
    }

    @Override
    @Transactional
    public void delete(String id) {
        addressRepository.deleteById(id);
    }
}
