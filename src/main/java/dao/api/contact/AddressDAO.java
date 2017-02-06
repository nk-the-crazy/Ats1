package dao.api.contact;

import org.springframework.data.jpa.repository.JpaRepository;

import model.contact.Address;

public interface AddressDAO  extends JpaRepository<Address, Long>
{

}
