package service.impl.contact;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;

import dao.api.contact.AddressDAO;
import dao.api.contact.ContactDAO;
import model.contact.Address;
import model.contact.Contact;
import service.api.contact.ContactManager;

@Service("contactManagerService")
@Transactional
public class ContactManagerImpl implements ContactManager
{
    //---------------------------------
    private static final Logger logger = LoggerFactory.getLogger(ContactManagerImpl.class);
    //---------------------------------
            
    @Autowired
    AddressDAO addressDAO;

    @Autowired
    ContactDAO contactDAO;

    
    /**************************************************
     * 
     */
    @Override
    public Address createAddress(int countryId, int regionId , String city, String addressLine, String secondaryAddress, int type)
    {
        Address address = null;
        
        try 
        {
            address = new Address();
            address.setRegionId( regionId );
            address.setCountryId( countryId );
            address.setCity( city );
            address.setAddressLine( addressLine );
            address.setSecondaryAddress( secondaryAddress );
            address.setType( type );
        }
        catch(Exception e) 
        {
            logger.error( "*** Error creating Address object:" , e);
        }
        
        return address;
    
    }
    
    
    /**************************************************
     * 
     */
    @Override
    public Contact createContact(  String phone, String email, String secondaryContacts, int type)
    {
        Contact contact = null;
        
        if(!Strings.isNullOrEmpty( email )) 
        {
            isValidEmail(email);
        }
        
        try 
        {
            contact = new Contact();
            contact.setSecondaryContacts( secondaryContacts );
            contact.setEmail( email );
            contact.setPhone( phone );
            contact.setType(type);
     
        }
        catch(Exception e) 
        {
            logger.error( "*** Error creating Contact object:" , e);
        }
        
        return contact;
    
    }
    
    
    
    /**************************************************
     * 
     */
    @Override
    public Contact saveContact( Contact contact)
    {
        if(!Strings.isNullOrEmpty( contact.getEmail() )) 
        {
            isValidEmail(contact.getEmail());
        }
        
        try 
        {
            return contactDAO.save( contact );
        }
        catch(Exception e) 
        {
            logger.error( "*** Error saving Contacts:" , e);
        }
        
        return null;
    }
    
    
    /**************************************************
     * 
     */
    @Override
    public Address saveAddress( Address address)
    {
        try 
        {
           return addressDAO.save( address );
        }
        catch(Exception e) 
        {
            logger.error( "*** Error saving address:" , e);
        }
        
        return null;
    }
    
    
    
    /**************************************************
     * 
     */
    private void isValidEmail( String email )
    {
        if ( Strings.isNullOrEmpty( email ) || email.length() < 4 )
        {
            throw new IllegalArgumentException( "Invalid email" );
        }

        if ( email.equalsIgnoreCase( "email" ))
        {
            throw new IllegalArgumentException( "EMAIL name is reserved by the system." );
        }
    }
    
    
}
