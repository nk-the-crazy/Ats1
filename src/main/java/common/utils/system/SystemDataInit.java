package common.utils.system;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import model.common.DataValue;
import model.contact.Address;
import model.contact.Contact;
import model.identity.Permission;
import model.identity.PermissionItem;
import model.identity.Role;
import model.identity.User;
import model.identity.UserType;
import model.organization.Organization;
import model.person.Person;
import service.api.contact.ContactManager;
import service.api.identity.IdentityManager;
import service.api.organization.OrganizationManager;
import service.api.person.PersonManager;

@Component
@DependsOn("SystemUtils")
@Transactional
public class SystemDataInit
{
    @Autowired
    private IdentityManager identityManager;
    
    @Autowired
    private PersonManager personManager;
    
    @Autowired
    private ContactManager contactManager;
    
    @Autowired
    private OrganizationManager organizationManager;
    

    @PostConstruct
    void init() 
    {
        initializeSystemData();
    } 
    //******************************************
    public void initializeSystemData() 
    {
        
        if(identityManager.getUser( 1 ) == null)
        {
            createDefaultUsers();
        }
    }

    //******************************************
    
    private void createDefaultUsers() 
    {
        Organization organization = createDefaultOrganizations();
        
        Role role1 = createDefaultRoles("Системный Администратор" ,1); 
        Role role2 = createDefaultRoles("Оператор задач (заданий)" ,1); 
        Role role3 = createDefaultRoles("Менеджер Тестирования", 2); 
        Role role4 = createDefaultRoles("Пользователь", 10); 
        
        User user = identityManager.createUser( "admin", "secret", "mail@mail.com", UserType.Regular.getId());
        Person person   = personManager.createPerson( "", "Administrator", "System", "" );
            
        Contact contact = contactManager.createContact( "312", "33-12-12", "", DataValue.Primary.getId() );
        Address address =  contactManager.createAddress( 1, 1, "Bishkek", "The addressLine","Secondary Address" , DataValue.Primary.getId() );
            
        person.setContact( contact );
        person.setAddress( address );
           
        person.setOrganization( organization );
            
        user.setPerson( person );
            
        user.addRole( role1 );
        user.addRole( role2 );
        user.addRole( role3 );
        user.addRole( role4 );
            
        identityManager.saveUser( user );
        
    }
    
    private Role createDefaultRoles(String name, int type) 
    {
        Permission perm1 = identityManager.createPermission( PermissionItem.IdentityManagement.getId(), true, true, false, false);
        Permission perm2 = identityManager.createPermission( PermissionItem.AssessmentManagement.getId(), true, true, false, false);
        Permission perm3 = identityManager.createPermission( PermissionItem.GroupManagement.getId(), true, true, false, false);
        Permission perm4 = identityManager.createPermission( PermissionItem.AssessmentTaskManagement.getId(), true, true, false, false);
        Permission perm5 = identityManager.createPermission( PermissionItem.ReportManagement.getId(), true, true, false, false);
        
        Role role = identityManager.createRole( name, "Details:"+name, 2);
        
        if(type == 10)
        {
            role.addPermission( identityManager.createPermission( PermissionItem.AssessmentTesting.getId() , true, true, false, false) );
        }
        else
        {
            role.addPermission( perm1 );
            role.addPermission( perm2 );
            role.addPermission( perm3 );
            role.addPermission( perm4 );
            role.addPermission( perm5 ); 
        }
        
        
        
        identityManager.saveRole( role );
        
        return role;
    }
    
    //******************************************
    private Organization createDefaultOrganizations() 
    {
        Organization organization = organizationManager.createOrganization("", "Министерство Финансов КР", 1 );
        
        Contact contact = contactManager.createContact( "312", "44-32-12", "", DataValue.Primary.getId() );
        Address address =  contactManager.createAddress( 1, 1, "Bishkek", "The MinFin addressLine1", "" , DataValue.Primary.getId() );
        
        organization.setAddress( address );
        organization.setContact( contact );
        
        return organizationManager.saveOrganization( organization );
    }

    

    
    
}