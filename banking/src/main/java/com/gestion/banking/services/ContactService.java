package com.gestion.banking.services;

import com.gestion.banking.dto.ContactDto;

import java.util.List;

public interface ContactService extends AbstractService<ContactDto>{
    List<ContactDto> findAllByUserId(String id);
}
