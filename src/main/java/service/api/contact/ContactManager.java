package service.api.contact;

import model.contact.Address;
import model.contact.Contact;

public interface ContactManager
{
    Contact createContact( String phone, String email, String secondaryContacts, int type );

    Contact saveContact( Contact contact );

    Address saveAddress( Address address );

    Address createAddress( int countryId, int regionId, String city, String primaryAddress, String secondaryAddress,
            int type );
}
