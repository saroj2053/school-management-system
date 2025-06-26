package com.sah.bright_future_academy.service;

import com.sah.bright_future_academy.constants.BrightFutureAcademyConstants;
import com.sah.bright_future_academy.controller.ContactController;
import com.sah.bright_future_academy.model.Contact;
import com.sah.bright_future_academy.repo.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.Optional;


@Service
public class ContactService {

    /* injecting contactRepository bean into contactService */
    @Autowired
    private ContactRepository contactRepository;


    private static Logger log = LoggerFactory.getLogger(ContactController.class);

    public boolean saveMessageDetails(Contact contact) {
        boolean isSaved = false;
        contact.setStatus(BrightFutureAcademyConstants.OPEN);
        Contact savedContact = contactRepository.save(contact);
        if (savedContact != null && savedContact.getContactId() > 0) {
            isSaved = true;
        }
        return isSaved;
    }

    public Page<Contact> findMsgsWithOpenStatus(int pageNum, String sortField, String sortDir) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(
                pageNum - 1,
                pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());
        Page<Contact> msgPage = contactRepository.findByStatus(BrightFutureAcademyConstants.OPEN, pageable);
        return msgPage;

    }

    public boolean updateMsgStatus(int contactId) {
        boolean isUpdated = false;
        Optional<Contact> contact = contactRepository.findById(contactId);
        contact.ifPresent(contact1 -> {
            contact1.setStatus(BrightFutureAcademyConstants.CLOSED);
        });
        Contact updatedContact = contactRepository.save(contact.get());
        if (updatedContact != null && updatedContact.getUpdatedBy() != null) {
            isUpdated = true;
        }

        return isUpdated;
    }
}
