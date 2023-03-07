package com.gestion.banking.services.impl;

import com.gestion.banking.dto.ContactDto;
import com.gestion.banking.repositories.ContactRepository;
import com.gestion.banking.services.ContactService;
import com.gestion.banking.validators.ObjectsValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ObjectsValidator<ContactDto> validator;

    public ContactServiceImpl(ContactRepository contactRepository, ObjectsValidator<ContactDto> validator) {
        this.contactRepository = contactRepository;
        this.validator = validator;
    }

    @Override
    @Transactional
    public String save(ContactDto dto) {
        validator.validate(dto);
        return contactRepository.saveAndFlush(ContactDto.toEntity(dto)).getId();
    }

    @Override
    public List<ContactDto> findAll() {
        return contactRepository.findAll()
                .stream()
                .map(ContactDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ContactDto findById(String id) {
        return contactRepository.findById(id)
                .map(ContactDto::toDto)
                .orElseThrow(()-> new EntityNotFoundException("No Contact was find with the ID : "+id));
    }

    @Override
    @Transactional
    public void delete(String id) {
        contactRepository.deleteById(id);
    }

    @Override
    public List<ContactDto> findAllByUserId(String id) {
        return contactRepository.findAllByUserId(id)
                .stream()
                .map(ContactDto::toDto)
                .collect(Collectors.toList());
    }
}
