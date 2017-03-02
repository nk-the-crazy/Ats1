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
        
        if(identityManager.getUser( 1 ) == null)
        {
            createDefaultUsers();
            createDefaultCategories();
            //createDefaultAssessments();
        }
    }

    //******************************************
    
    private void createDefaultUsers() 
    {
        Organization organization = createDefaultOrganizations();
        
        UserGroup group = groupManager.createGroup( "Группа Аудиторов г.Бишкек", "Группа Аудиторов - Бишкек 1", 1 );
        groupManager.saveGroup( group );
        
        UserGroup group1 = groupManager.createGroup( "Группа Аудиторов г. Ош", "Группа Аудиторов - Ош 2", 1 );
        groupManager.saveGroup( group1 );
        
        UserGroup group2 = groupManager.createGroup( "Группа Аудиторов г. ЖА", "Группа Аудиторов - ЖА 3", 1 );
        groupManager.saveGroup( group2 );
        
        Role role1 = createDefaultRoles("Системный Администратор" ,1); 
        Role role2 = createDefaultRoles("Оператор задач (заданий)" ,1); 
        Role role3 = createDefaultRoles("Менеджер Тестирования", 2); 
        Role role4 = createDefaultRoles("Пользователь", 10); 
        
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

    
    //******************************************
    private AssessmentTaskCategory createDefaultCategory(String name , String details , AssessmentTaskCategory parent) 
    {
        AssessmentTaskCategory cat = taskManager.createTaskCategory( name, details ,2 );
        cat.setParent( parent );
        return taskManager.saveTaskCategory( cat );
    }    
    //******************************************

    private void createDefaultCategories() 
    {
        /*
        Блок 1.Система управления государственными финансами
        Финансовое управление и контроль
        Внутренний контроль
        Бухгалтерский учет и отчетность
        Бюджетирование
        Государственные закупки
        Блок 2. Базовые знания по внутреннему аудиту
        Законодательные основы проведения внутреннего аудита в Кыргызской Республике
        Обязательные аспекты Международных стандартов профессиональной практики внутреннего аудита (IPPF)
        Понятие риска и процедур внутренних контролей
        Механизмы и техники проведения внутренних аудитов
        Блок 3. Практика проведения внутренних аудитов
        Управление функцией внутреннего аудита
        Стратегическая и операционная роли внутреннего аудита в системе управления организацией
        Разработка риск-ориентированного плана деятельности подразделения внутреннего аудита
        Виды (типы) аудита
        Аудиторские техники в контексте применения компьютерных технологий
        Пошаговое управление задачами по внутреннему аудиту (планирование, проведение, наблюдение написание аудиторского отчета (формат) и мониторинг результатов
        Риски мошенничества и соответствующие контрольные процедуры
         Блок 4. Элементы необходимых знаний по внутреннему аудиту
        Управление и управленческая этика
        Управление рисками
        Организационная структура
        Управление финансами
        Управленческие процессы и соответствующие риски
        Принципы управления и лидерства, коммуникационная политика
        Информационные технологии и непрерывность управленческого процесса в контексте построения эффективной системы внутреннего контроля
        Блок 5. Оценка качества внутреннего аудита
        Внутренние оценки
        Внешние оценки

        */
        
        
        AssessmentTaskCategory cat1 = createDefaultCategory("1.Система управления гос. финансами" , 
                                                            "Система управления государственными финансами", null); 
        
        AssessmentTaskCategory catX = createDefaultCategory("1.1 Финансовое управление и контроль" , 
                        "Финансовое управление и контроль", cat1); 
        
        //*************************************
        for(int x=1;x < 21; x++)
        {
            AssessmentTask task = createDefaultTasks(x,1);
            
            if(x > 10)
            {
                task = createDefaultTasks(x,2);
            }
            catX.addTask( task );
        }
        taskManager.saveTaskCategory( catX );
        
        createDefaultCategory("1.2 Внутренний контроль" , 
                        "Внутренний контроль", cat1); 
        
        createDefaultCategory("1.3 Бухгалтерский учет и отчетность" , 
                        "Бухгалтерский учет и отчетность", cat1); 
        
        createDefaultCategory("1.4 Бюджетирование" , 
                        "Бюджетирование", cat1); 
        
        createDefaultCategory("1.5 Государственные закупки" , 
                        "Государственные закупки", cat1); 
        
        AssessmentTaskCategory cat2 = createDefaultCategory("2.Базовые знания по внутреннему аудиту" , 
                                                            "Базовые знания по внутреннему аудиту", null); 

        createDefaultCategory("2.1 Законодательные основы" , 
                        "Законодательные основы проведения внутреннего аудита в Кыргызской Республике", cat2); 
        
        createDefaultCategory("2.2 Обязательные аспекты Международных стандартов" , 
                        "Обязательные аспекты Международных стандартов профессиональной практики внутреннего аудита (IPPF)", cat2); 
        
        createDefaultCategory("2.3 Понятие риска и процедур внутренних контролей" , 
                        "Понятие риска и процедур внутренних контролей", cat2); 
        
        createDefaultCategory("2.4 Обязательные аспекты Международных стандартов" , 
                        "Механизмы и техники проведения внутренних аудитов", cat2); 
        
        AssessmentTaskCategory cat3 = createDefaultCategory("3.Практика проведения внутренних аудитов" , 
                                                            "Практика проведения внутренних аудитов", null); 


        createDefaultCategory("3.1 Управление функцией внутреннего аудита" , 
                        "Управление функцией внутреннего аудита", cat3); 
       
    }
    
    
    //******************************************
    private AssessmentTask createDefaultTasks(int index, int type) 
    {
        AssessmentTask task = taskManager.createTask( "Вопрос №-" + index, index+"+3 = ?",10, 2, type, 1);
                
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