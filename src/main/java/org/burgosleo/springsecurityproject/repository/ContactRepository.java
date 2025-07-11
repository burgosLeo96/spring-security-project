package org.burgosleo.springsecurityproject.repository;

import org.burgosleo.springsecurityproject.model.Contact;
import org.springframework.data.repository.CrudRepository;

public interface ContactRepository extends CrudRepository<Contact, Long> {

}
