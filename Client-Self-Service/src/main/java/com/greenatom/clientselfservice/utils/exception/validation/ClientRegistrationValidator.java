package com.greenatom.clientselfservice.utils.exception.validation;

import com.greenatom.clientselfservice.domain.dto.security.ClientRegistrationDTO;
import com.greenatom.clientselfservice.domain.entity.Client;
import com.greenatom.clientselfservice.service.impl.ClientService;
import com.greenatom.clientselfservice.utils.exception.AuthException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClientRegistrationValidator implements Validator {

    private final ClientService clientService;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return ClientRegistrationDTO.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target,@NonNull Errors errors) {
        ClientRegistrationDTO client = (ClientRegistrationDTO) target;
        Optional<Client> clientFromDb = clientService.findByEmail(client.getEmail());
        if(clientFromDb.isPresent()){
            throw AuthException.CODE.EMAIL_IN_USE.get();
        }
    }
}
