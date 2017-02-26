package common.utils.system;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import org.apache.commons.lang.time.DateUtils;

import model.assessment.Assessment;
import model.assessment.task.AssessmentTask;
import model.assessment.task.AssessmentTaskCategory;
import model.assessment.task.AssessmentTaskDetail;
import model.common.DataValue;
import model.contact.Address;
import model.contact.Contact;
import model.group.UserGroup;
import model.identity.Permission;
import model.identity.PermissionItem;
import model.identity.Role;
import model.identity.User;
import model.identity.UserType;
import model.organization.Organization;
import model.person.Person;
import service.api.assessment.AssessmentManager;
import service.api.assessment.AssessmentTaskManager;
import service.api.contact.ContactManager;
import service.api.group.GroupManager;
import service.api.identity.IdentityManager;
import service.api.organization.OrganizationManager;
import service.api.person.PersonManager;

@Component
@Transactional
public class SystemDataInit
{
    @Autowired
    private IdentityManager identityManager;
    
    @Autowired
    private PersonManager personManager;
    
    @Autowired
    private AssessmentTaskManager taskManager;
    
    @Autowired
    private ContactManager contactManager;
    
    @Autowired
    private GroupManager groupManager;
    
    @Autowired
    private OrganizationManager organizationManager;
    
    @Autowired
    private AssessmentManager assessmentManager;
    

    @PostConstruct
    void init() 
    {
        initializeSystemData();
    } 
    //******************************************
    public void initializeSystemData() 
    {
        createDefaultUsers();
        createDefaultCategories();
        createDefaultAssessments();
    }

    //******************************************
    
    private void createDefaultUsers() 
    {
        Organization organization = createDefaultOrganizations();
        
        UserGroup group = groupManager.createGroup( "Группа Аудиторов 1", "Группа Аудиторов - Desc 1", 1 );
        groupManager.saveGroup( group );
        
        UserGroup group1 = groupManager.createGroup( "Группа Аудиторов 2", "Группа Аудиторов - Desc 2", 1 );
        groupManager.saveGroup( group1 );
        
        UserGroup group2 = groupManager.createGroup( "Группа Аудиторов 3", "Группа Аудиторов - Desc 3", 1 );
        groupManager.saveGroup( group2 );
        
        Role role1 = createDefaultRoles("Administrator Role"); 
        Role role2 = createDefaultRoles("Task Manager"); 
        Role role3 = createDefaultRoles("Assessment Manager"); 
        Role role4 = createDefaultRoles("User Role"); 
        
        for(int x=1; x < 30; x++) 
        {
            User user = identityManager.createUser( "admin"+x, "secret", "mail@mail.com"+x, UserType.Regular.getId());
            Person person   = personManager.createPerson( "", "Administrator"+x, "System"+x, "" );
            
            Contact contact = contactManager.createContact( "312", "33-12-12", "", DataValue.Primary.getId() );
            Address address =  contactManager.createAddress( 1, 1, "Bishkek", "The addressLine","Secondary Address" , DataValue.Primary.getId() );
            
            person.setContact( contact );
            person.setAddress( address );
           
            person.setOrganization( organization );
            
            user.setPerson( person );
            user.addGroup( group1 );
            user.addGroup( group2 );
            
            user.addRole( role1 );
            user.addRole( role2 );
            user.addRole( role3 );
            user.addRole( role4 );
            
            if(x == 3 || x == 6) 
            {
                UserGroup group3 = groupManager.createGroup( "Группа Аудиторов - 4"+x, "Группа Аудиторов - Desc 4"+x, 1 );
                groupManager.saveGroup( group3 );
                
                user.addGroup( group3 );
            }
            
            identityManager.saveUser( user );
        }
    }
    
    private Role createDefaultRoles(String name) 
    {
        Permission perm1 = identityManager.createPermission( PermissionItem.IdentityManagement.getId(), true, true, false, false);
        Permission perm2 = identityManager.createPermission( PermissionItem.AssessmentManagement.getId(), true, true, false, false);
        Permission perm3 = identityManager.createPermission( PermissionItem.GroupManagement.getId(), true, true, false, false);
        Permission perm4 = identityManager.createPermission( PermissionItem.AssessmentTaskManagement.getId(), true, true, false, false);
        
        Role role = identityManager.createRole( name, "Details:"+name, 2);
        
        role.addPermission( perm1 );
        role.addPermission( perm2 );
        role.addPermission( perm3 );
        role.addPermission( perm4 );
        
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

    
    //******************************************
    private void createDefaultCategories() 
    {
        for(int x=1; x < 20; x++) 
        {
            AssessmentTaskCategory category = null;
            
            if(x == 1)
            {
                category = taskManager.createTaskCategory( "Default-"+x, "Default Category-"+x , 1);
            }
            else
            {
                category = taskManager.createTaskCategory( "Category-"+x, "Details of Category-"+x , 2);
                
                AssessmentTask task1 = null;
                AssessmentTask task2 = null; 
                
                if(x < 10)
                {
                    task1 = createDefaultTasks(x , 1) ;
                    task2 = createDefaultTasks(x+1, 2) ;
                }
                else
                {
                    task1 = createDefaultTasks(x , 1) ;
                    task2 = createDefaultTasks(x+1, 2) ;
                }
                
                category.addTask( task1 );
                category.addTask( task2 );
                
                for(int a=1; a < 5; a++) 
                {
                    AssessmentTaskCategory categoryS1 = taskManager.createTaskCategory( "Category-"+x+"-"+a, "Details of Category-"+x , 2 );
                    category.addChildCategory( categoryS1 );
    
                    for(int b=1; b < 3; b++) 
                    {
                        AssessmentTaskCategory categoryS2 = taskManager.createTaskCategory( "Category-"+x+"-"+a+"-"+b, "Details of Category-"+x, 2 );
                        categoryS1.addChildCategory( categoryS2 );
    
                        //AssessmentTaskCategory categoryS3 = taskManager.createTaskCategory( "Category-"+x+"-"+a+"-"+b+"-"+b, "Details of Category-"+x );
                        //categoryS2.addChildCategory( categoryS3 );
                    }
                }
                
                
            }
            taskManager.saveTaskCategory( category );
        }
    }
    
    
    //******************************************
    private AssessmentTask createDefaultTasks(int index, int type) 
    {
        AssessmentTask task = taskManager.createTask( "Task Item name-1" + index, index+"+3 = ?",10, 2, type, 1);
                
        AssessmentTaskDetail det1 = taskManager.createTaskDetails( "Answer = "+ (index+3), 100 );
        AssessmentTaskDetail det2 = taskManager.createTaskDetails( "Answer = "+ (index+4), 0 );
        AssessmentTaskDetail det3 = taskManager.createTaskDetails( "Answer = "+ (index+14), 0 );
        AssessmentTaskDetail det4 = taskManager.createTaskDetails( "Answer = "+ (index+33), 0 );
        
        task.addDetail( det1 );
        task.addDetail( det2 );
        task.addDetail( det3 );
        task.addDetail( det4 );
        
        //taskManager.saveTask( task );
        
        return task;
    }
    
    //******************************************
    private void createDefaultAssessments() 
    {
        
        for(int x=1;x < 10 ; x++  ) 
        {
            Date startDate = DateUtils.addDays( new Date(System.currentTimeMillis()), -1 );
            Date endDate = DateUtils.addDays( new Date(System.currentTimeMillis()), x );
            Assessment asmt =  assessmentManager.createAssessment( "New Assessment-"+x, startDate , endDate,10 , 2 );
            
            asmt.setAuthor( identityManager.getUser( 1 ) );
            
            if(x == 9 || x == 8)
            {
                asmt.addParticipant( groupManager.getGroupById( 5 ) );
                asmt.addTask( taskManager.getTaskById( 1 ) );
                asmt.addTask( taskManager.getTaskById( 5 ) );
            }
            else
            {
                asmt.addParticipant( groupManager.getGroupById( 1 ) );
                asmt.addParticipant( groupManager.getGroupById( 2 ) );
                asmt.addParticipant( groupManager.getGroupById( 3 ) );
                
                
                asmt.addTask( taskManager.getTaskById( 1 ) );
                asmt.addTask( taskManager.getTaskById( 2 ) );
                asmt.addTask( taskManager.getTaskById( 3 ) );
                asmt.addTask( taskManager.getTaskById( 4 ) );
            }
            assessmentManager.saveAssessment( asmt );
        }
    }
    
}