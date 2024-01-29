package com.example.crudapp.validator;

import com.example.crudapp.exception.AuthCustomException;
import com.example.crudapp.repo.AuthRepository;
import com.example.crudapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthValidator {

    @Autowired
    private AuthRepository repository;

    @Autowired
    private AuthService service;

    @Override
    public void validate(Object object) {
        if (!(object instanceof String)) {
            throw new AuthCustomException("Authentication Token not of expected type");
        }

        String authenticationToken = (String)object;
        if (!service.isValidAuthToken(authenticationToken)) {
            throw new AuthCustomException("Authentication Token is Invalid");
        }
    }
}
