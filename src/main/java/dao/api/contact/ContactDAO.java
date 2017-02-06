package dao.api.contact;

import org.springframework.data.jpa.repository.JpaRepository;

import model.contact.Contact;


public interface ContactDAO  extends JpaRepository<Contact, Long>
{

}
