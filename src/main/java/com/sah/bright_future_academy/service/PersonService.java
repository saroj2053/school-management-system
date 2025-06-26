package com.sah.bright_future_academy.service;

import com.sah.bright_future_academy.constants.BrightFutureAcademyConstants;
import com.sah.bright_future_academy.model.Person;
import com.sah.bright_future_academy.model.Roles;
import com.sah.bright_future_academy.repo.PersonRepository;
import com.sah.bright_future_academy.repo.RolesRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired // [ Step-2: injecting a bean of type PasswordEncoder ]
    private PasswordEncoder passwordEncoder;

    public boolean createNewPerson(@Valid Person person) {
        boolean isSaved = false;
        Roles role = rolesRepository.getByRoleName(BrightFutureAcademyConstants.STUDENT_ROLE) ;
        person.setRoles(role);
        person.setPwd(passwordEncoder.encode(person.getPwd())); // hashing password
        person = personRepository.save(person);
        log.info(person.toString());
        if(person != null && person.getPersonId() > 0) {
            isSaved = true;
        }

        return isSaved;
    }
}
