package service.impl.person;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;

import dao.api.person.PersonDAO;
import model.person.Person;
import service.api.person.PersonManager;

@Service("personManagerService")
@Transactional
public class PersonManagerImpl implements PersonManager
{
    //---------------------------------
    private static final Logger logger = LoggerFactory.getLogger(PersonManagerImpl.class);
    //---------------------------------
    
    @Autowired
    PersonDAO personDAO;
    
    
    /**************************************************
     * 
     */
    @Override
    public Person createPerson(String code, String firstName, String lastName,String middleName)
    {
        Person person = null;
        
        try 
        {
            if(Strings.isNullOrEmpty( code ))
                code = generateCode();
                    
            isValidCode(code);
            
            person = new Person();
            person.setCode( code );
            person.setFirstName( firstName );
            person.setLastName( lastName );
            person.setMiddleName( middleName );
          
        }
        catch(Exception e) 
        {
            logger.error( "Error creating Person object:" , e );   
        }
        
        return person;
    
    }
    
    
    /**************************************************
     * 
     */
    @Override
    public Person savePerson(Person person)
    {
        try 
        {
            if(Strings.isNullOrEmpty( person.getCode() ))
                person.setCode( generateCode());
                    
            isValidCode(person.getCode());
            
            return personDAO.save( person );
        }
        catch(Exception e) 
        {
            logger.error( "Error creating Person object:" , e );   
            return null;
        }
    }
    
    
    
    
    /**************************************************
     * 
     */
    @Override
    public String generateCode()
    {
        String code = "F";
        try
        {
            Integer maxId = personDAO.getMaxId();
            maxId = (maxId == null) ? 1 : (maxId + 1);

            code = code + String.format("%05d", maxId);
        }   
        catch(Exception e)
        {
            logger.error(" ***** Error  generating Code:", e);
        }
        
        return code;
    }
    
    
    /**************************************************
     * 
     */
    private void isValidCode( String code )
    {
        if ( Strings.isNullOrEmpty( code ) || code.length() < 4 )
        {
            throw new IllegalArgumentException( "Code name cannot be shorter than 4 characters." );
        }

        if ( code.equalsIgnoreCase( "code" ))
        {
            throw new IllegalArgumentException( "Code name is reserved by the system." );
        }
    }


    /**************************************************
     * 
     */
    @Override
    public Page<Person> getPersonListByFirstNameAndLastName( String firstName, String lastName, Pageable pageable )
    {
        return null;
    }
    
   
}
