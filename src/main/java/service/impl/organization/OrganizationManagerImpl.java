package service.impl.organization;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import dao.api.organization.OrganizationDAO;
import dao.api.person.PersonDAO;
import model.organization.Organization;
import model.person.Person;
import service.api.organization.OrganizationManager;

@Service("organizationManagerService")
@Transactional
public class OrganizationManagerImpl implements OrganizationManager
{
    //---------------------------------
    private static final Logger logger = LoggerFactory.getLogger(OrganizationManagerImpl.class);
    //---------------------------------
            
    @Autowired
    OrganizationDAO organzDAO;

    @Autowired
    PersonDAO personDAO;

    
    /**************************************************
     * 
     */
    @Override
    public Organization createOrganization(String code, String name, int type)
    {
        Organization organization = null;
        
        if(Strings.isNullOrEmpty( code ))
            code = generateCode();
                
        isValidCode(code);
        isValidName(name);
        
        try 
        {
            organization = new Organization();
            organization.setCode( code );
            organization.setName( name ); 
            organization.setType( type );
        }
        catch(Exception e) 
        {
            
        }
        
        return organization;
    
    }
    
    
    /**************************************************
     * 
     */
    @Override
    public Organization saveOrganization( Organization organization )
    {
        if(Strings.isNullOrEmpty( organization.getCode() ))
            organization.setCode(generateCode());
                
        isValidCode(organization.getCode());
        isValidName(organization.getName());

        try
        {
            return organzDAO.save( organization );
        }
        catch ( Exception e )
        {
            logger.error( " **** Error in Save Organization", e );        
        }

        return null;
    }


    /* *************************************************
     */
    @Override
    public Page<Organization> getOrganizations(Pageable pageable)
    {
        return organzDAO.findAll( pageable );
    }


    /* *************************************************
     */
    @Override
    public List<Organization> getOrganizations()
    {
        return organzDAO.findAll();
    }
    
    
    /**************************************************
     * 
     */
    @Override
    public List<Organization> getOrganizationsByName(String organizationName)
    {
        return organzDAO.findByName( organizationName ) ;
    }


    /**************************************************
     * 
     */
    @Override
    public List<Organization> getOrganizationShortListByName(String organizationName)
    {
        return organzDAO.getShortListByName( organizationName );
    }

    
    /**************************************************
     * 
     */
    @Override
    public Organization getOrganizationById( long organizationId )
    {
        return organzDAO.getOne( organizationId );
    }
    

    /**************************************************
     * 
     */
    @Override
    public Organization getOrganizationDetails( long organizationId )
    {
        return organzDAO.getFullDetails( organizationId );
    }


    /**************************************************
     * 
     */
    @Override
    public Page<Organization> getOrganizationsByName( String organizationName, Pageable pageable )
    {
        return organzDAO.findByName( organizationName, pageable );
    }
    
    

    /**************************************************
     * 
     */
    @Override
    public Page<Person> getOrganizationPersonList( long organizationId, Pageable pageable )
    {
        try 
        {
            return personDAO.getByOrganizationId( organizationId, pageable );
        }
        catch(Exception e)
        {
            logger.error( " **** Error in gettting Orgzanization Person list:", e );
        }
        
        return null;
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
    private void isValidName( String name )
    {
        if ( Strings.isNullOrEmpty( name ) || name.length() < 3 )
        {
            throw new IllegalArgumentException( "Code name cannot be shorter than 4 characters." );
        }

        if ( name.equalsIgnoreCase( "name" ))
        {
            throw new IllegalArgumentException( "Name is reserved by the system." );
        }
    }
    
    
    /**************************************************
     * 
     */
    @Override
    public String generateCode()
    {
        String code = "L";
        try
        {
            Integer maxId = organzDAO.getMaxId();
            maxId = (maxId == null) ? 1 : (maxId + 1);
            
            code = code + String.format("%05d", maxId);
        }   
        catch(Exception e)
        {
            logger.error(" ***** Error  generating Code:", e);
        }
        
        return code;
    }

}
