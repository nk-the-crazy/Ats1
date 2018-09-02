package web.controller.identity;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import model.common.session.SessionData;
import model.group.UserGroup;
import model.identity.Role;
import model.identity.User;
import service.api.group.GroupManager;
import service.api.identity.IdentityManager;
import service.api.organization.OrganizationManager;
import web.model.identity.UserExportParams;
import web.view.ModelView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import common.exceptions.security.InvalidLoginException;
import common.exceptions.security.InvalidPasswordException;
import common.utils.StringUtils;


@Controller
public class IdentityController
{
    //---------------------------------
    private static final Logger logger = LoggerFactory.getLogger(IdentityController.class);
    //---------------------------------

    @Autowired
	private IdentityManager identityManager;
    
    @Autowired
    private OrganizationManager organizationManager;
    
    @Autowired
    private GroupManager groupManager;

      
	
    /*******************************************************
     * 
     * */
    @InitBinder
    protected void initBinder(WebDataBinder binder) 
    {
        SimpleDateFormat dateFormat = StringUtils.getShortDateFormat();
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
    
    
    /*******************************************************
	 * 
	 * */
	@RequestMapping(value={ "/", "/login.vw"} )
	public String login()
	{
		return ModelView.VIEW_LOGIN_PAGE;
	}
	
	
    /*******************************************************
     * 
     * */
    @RequestMapping(value={ "/login_system.vw"} )
    public String sysLogin()
    {
        return ModelView.VIEW_SYSTEM_LOGIN_PAGE;
    }
    
	
	/*******************************************************
	 * 
	 * */
	@RequestMapping( "/main.vw" )
	public String mainView( @AuthenticationPrincipal SessionData sData )
	{
		if( sData != null ) 
		{
		    if( sData.getUser() != null && sData.getUser().getAssessmentId() > 0 ) 
		    {
	            return "redirect:test_process_init.do?assessment_id=" + sData.getUser().getAssessmentId();
		    }
		}
		
	    return ModelView.VIEW_MAIN_PAGE;
	}
	
	
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/edit_password.vw")
    public String changePasswordView()
    {
        return ModelView.VIEW_EDIT_PASSWORD_PAGE;
    }
    
    
    /*******************************************************
     * 
     * */
    @RequestMapping( value = "/edit_password.do", method = RequestMethod.POST )
    public ModelAndView changeUserPassword( @RequestParam( "currentPassword" ) String currentPassword,
                                            @RequestParam( "newPassword" ) String newPassword,
                                            @AuthenticationPrincipal SessionData sData)
    {
        
        ModelAndView model = new ModelAndView( ModelView.VIEW_EDIT_PASSWORD_PAGE );
        
        try
        {
            if(sData == null)
            {
                throw new InvalidLoginException("Session does not exists");
            }
            else
            {
                if(identityManager.changeUserPassword( sData.getUser(), currentPassword, newPassword ))
                {
                    model.addObject( "errorMessage", "message.success.password.changed");
                }
            }
        }
        catch(InvalidLoginException e)
        {
            model.addObject( "errorMessage", "message.error.invalid_login");
        }
        catch(InvalidPasswordException e)
        {
            model.addObject( "errorMessage", "message.error.password.invalid");
        }
        catch(Exception e)
        {
            logger.error( " **** Error changing password for user:" + sData.getUser().getUserName() , e );    
            model.addObject( "errorMessage", "message.error.system" );
        }
        
        return model;
        
    }
    
    
    
    /*******************************************************
     * 
     */
	@RequestMapping( value = "/user_list.vw")
    public ModelAndView getUsers( @RequestParam( name = "userName" , defaultValue = "", required = false ) 
                                  String userName, 
                                  @RequestParam( name = "lastName" , defaultValue = "", required = false ) 
                                  String lastName, 
                                  Pageable pageable )
    {
	    ModelAndView model = new ModelAndView( ModelView.VIEW_MAIN_PAGE );
	        
        try
        {
            Page<User> usersPage = identityManager.getUsersByUserNameAndLastName( userName, lastName, pageable);
                    
            model.addObject( "usersPage", usersPage );
            model.setViewName( ModelView.VIEW_USER_LIST_PAGE);
        }
        catch(Exception e)
        {
            logger.error( " **** Error getting user list:", e );        
        }
        
        return model;
    }
	
	
	/*******************************************************
     * 
     */
    @RequestMapping( value = "/user_details.vw")
    public ModelAndView getUserDetailsView(@RequestParam( "user_id" ) long userId, Pageable pageable)
    {
        ModelAndView model = new ModelAndView( ModelView.VIEW_MAIN_PAGE );
        
        try
        {
            User user = identityManager.getUserFullDetails( userId);
            Page<UserGroup> userGroups = identityManager.getUserGroups( userId, pageable );
            
            model.addObject( "userDetails", user );
            model.addObject( "userGroups", userGroups );
            model.setViewName( ModelView.VIEW_USER_DETAILS_PAGE);
        }
        catch(Exception e)
        {
            logger.error( " **** Error getting user Details:", e );        
        }
        
        return model;
        
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping("/user_edit.vw")
    public String editUserView(@RequestParam( "user_id" ) long userId, Model model , HttpSession session)
    {
        
        User userDetails = identityManager.getUserFullDetails( userId);
        model.addAttribute( "userDetails" , userDetails );
        model.addAttribute( "userGroupIds" , identityManager.getUserGroupIds( userId ) );
        model.addAttribute( "organizationShortList" , organizationManager.getOrganizationShortListByName( "" ));
        model.addAttribute( "roleShortList" , identityManager.getRoleShortListByRoleName( "" ));
        model.addAttribute( "groupShortList", groupManager.getGroupShortListByName( "" ));
        
        session.setAttribute( "psw", userDetails.getPassword() );
        session.setAttribute( "slt", userDetails.getSalt() );
        
        return ModelView.VIEW_USER_EDIT_PAGE;
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/user_edit.do")
    public String editUser( @ModelAttribute( "user" ) User user, Model model , HttpSession session)
    {

        try
        {
            String pws = (String)session.getAttribute( "psw" );
            String slt = (String)session.getAttribute( "slt" );
            
            user.setPassword( pws );
            user.setSalt( slt );
            
            session.removeAttribute( "psw" );
            session.removeAttribute( "slt" );
            
            user = identityManager.updateUser( user );
            
            return "redirect:user_details.vw?user_id=" + user.getId();
        }
        catch(IllegalArgumentException e)
        {
            model.addAttribute( "errorMessage", "message.error.attribute.invalid");
        }
        catch(Exception e)
        {
            logger.error( " **** Error editing user:", e ); 
            model.addAttribute( "errorMessage", "message.error.system" );
        }
        
        return editRoleView(user.getId(),model);

        
    }
    
    
	
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/role_list.vw")
    public ModelAndView getRoles( @RequestParam( name = "roleName" , defaultValue = "", required = false ) 
                                  String roleName,
                                  Pageable pageable )
    {
         ModelAndView model = new ModelAndView( ModelView.VIEW_MAIN_PAGE );
            
            try
            {
                Page<Role> rolesPage = identityManager.getRolesByRoleName( roleName, pageable);
                        
                model.addObject( "rolesPage", rolesPage );
                model.setViewName( ModelView.VIEW_ROLE_LIST_PAGE);
            }
            catch(Exception e)
            {
                logger.error( " **** Error getting role list:", e );        
            }
            
            return model;
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/role_details.vw")
    public ModelAndView getRoleDetailsView(@RequestParam( "role_id" ) long roleId, Pageable pageable)
    {
        ModelAndView model = new ModelAndView( ModelView.VIEW_MAIN_PAGE );
        
        try
        {
            Role role = identityManager.getRoleDetails( roleId);
            Page<User> usersPage = identityManager.getRoleUsers( roleId, pageable );
            
            model.addObject( "roleDetails", role );
            model.addObject( "usersPage", usersPage );
            model.setViewName( ModelView.VIEW_ROLE_DETAILS_PAGE);
        }
        catch(Exception e)
        {
            logger.error( " **** Error getting role Details:", e );        
        }
        
        return model;
        
    }
    
	
	
	/*******************************************************
     * 
     */
    @RequestMapping("/role_register.vw")
    public String registerRoleView()
    {
        return ModelView.VIEW_ROLE_REGISTER_PAGE;
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping("/role_edit.vw")
    public String editRoleView(@RequestParam( "role_id" ) long roleId, Model model)
    {
        model.addAttribute( "roleDetails" , identityManager.getRoleDetails( roleId ) );
        return ModelView.VIEW_ROLE_EDIT_PAGE;
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/role_edit.do")
    public String editRole( @ModelAttribute( "role" ) Role role, Model model)
    {

        try
        {
            role = identityManager.saveRole( role );
            
            return "redirect:role_details.vw?role_id=" + role.getId();
        }
        catch(IllegalArgumentException e)
        {
            model.addAttribute( "errorMessage", "message.error.attribute.invalid");
        }
        catch(Exception e)
        {
            logger.error( " **** Error registering assessment:", e ); 
            model.addAttribute( "errorMessage", "message.error.system" );
        }
        
        return editRoleView(role.getId(),model);

        
    }
    
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/role_register.do")
    public ModelAndView registerRole( @ModelAttribute( "role" ) Role role)
    {
        ModelAndView model = new ModelAndView( ModelView.VIEW_ROLE_REGISTER_PAGE );
        
        try
        {
            role = identityManager.saveRole( role );
            
            return new ModelAndView("redirect:role_details.vw?role_id=" + role.getId() );
        }
        catch(IllegalArgumentException e)
        {
            model.addObject( "errorMessage", "message.error.attribute.invalid");
        }
        catch(Exception e)
        {
            logger.error( " **** Error editing role:", e ); 
            model.addObject( "errorMessage", "message.error.system" );
        }
        
        return model;
        
    }
	
	
    /*******************************************************
     * 
     */
    @RequestMapping("/user_register.vw")
    public String registerUserView(Model model)
    {
        model.addAttribute( "organizationShortList" , organizationManager.getOrganizationShortListByName( "" ));
        model.addAttribute( "roleShortList" , identityManager.getRoleShortListByRoleName( "" ));
        model.addAttribute( "groupShortList", groupManager.getGroupShortListByName( "" ));
        
        return ModelView.VIEW_USER_REGISTER_PAGE;
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/user_register.do")
    public String registerUser( Model model, @ModelAttribute( "user" ) User user,
                                      @RequestParam( name = "roleIds" , required = false ) List<Long> roleIds,
                                      @RequestParam( name = "groupIds", required = false ) List<Long> groupIds)
    {
        try
        {
           
            user = identityManager.saveUser( user, roleIds, groupIds );
            return "redirect:user_details.vw?user_id=" + user.getId();
        }
        catch(InvalidPasswordException e)
        {
            model.addAttribute( "errorMessage", "message.error.password.invalid");
        }
        catch(IllegalArgumentException e)
        {
            model.addAttribute( "errorMessage", "message.error.attribute.invalid");
        }
        catch(Exception e)
        {
            logger.error( " **** Error registering user:", e ); 
            model.addAttribute( "errorMessage", "message.error.system" );
        }
        
        return registerUserView(model);
        
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping("/user_import.vw")
    public String importUserView(Model model)
    {
        List<UserGroup> groups = groupManager.getGroups();
        model.addAttribute( "userGroups", groups);
        
        return ModelView.VIEW_USER_IMPORT_PAGE;
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/user_import.do")
    public String importUser( @RequestParam("file") MultipartFile file,
                              @RequestParam( "group_name" ) String groupName, Model model)
    {
        try
        {
            if (!file.isEmpty()) 
            {
                identityManager.importUsers( file,groupName );
                return "redirect:user_list.vw";
            }
            else
            {
                throw new IllegalStateException("Invalid File");
            }
        } 
        catch (IllegalStateException e) 
        {
            model.addAttribute("errorMessage", "message.error.upload.file.invalid");
        } 
        catch(IllegalArgumentException e)
        {
            model.addAttribute("errorMessage", "message.error.attribute.invalid");
        }
        catch(Exception e)
        {
            logger.error( " **** Error importing user data:", e ); 
            model.addAttribute( "errorMessage", "message.error.system" );
        }
        
        return importUserView(model);
        
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/user_list.mvw")
    public ModelAndView getUserListView(@RequestParam( name = "submitUrl", required = false ) String submitUrl)
    {
        ModelAndView model = new ModelAndView( ModelView.VIEW_SYSTEM_ERROR_PAGE );
        
        try
        {
            model.setViewName( ModelView.VIEW_USER_LIST_MD_PAGE); 
        }
        catch(Exception e)
        {
            logger.error( " **** Error getting user list modal :", e ); 
            model.addObject( "errorData", "message.error.system");
            model.addObject( "errorDetails", e.toString() );        }
        
        return model;
        
    }

    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/user_details_export.mvw")
    public ModelAndView getUserlistExportView()
    {
        ModelAndView model = new ModelAndView( ModelView.VIEW_SYSTEM_ERROR_PAGE );
        
        try
        {
            model.setViewName( ModelView.VIEW_USER_EXPORT_MD_PAGE); 
        }
        catch(Exception e)
        {
            logger.error( " **** Error getting user list export modal :", e ); 
            model.addObject( "errorData", "message.error.system");
            model.addObject( "errorDetails", e.toString() );        }
        
        return model;
        
    }
    
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/user_details_export.do")
    public String exportUserDetails( @ModelAttribute( "userExportParams" ) UserExportParams exportParams, Model model)
    {
        try
        {
            model.addAttribute( "userDetailsList" , identityManager.getUserFullDetailsList() );
            
            if(exportParams.getOutputType() == 2)
                return ModelView.VIEW_USER_DETAILS_XLS;
            else if(exportParams.getOutputType() == 4)
                return ModelView.VIEW_USER_DETAILS_PDF;
        }
        catch(IllegalArgumentException e)
        {
            model.addAttribute( "errorMessage", "message.error.attribute.invalid");
        }
        catch(Exception e)
        {
            logger.error( " **** Error editing user:", e ); 
            model.addAttribute( "errorMessage", "message.error.system" );
        }
        
        return ModelView.VIEW_SYSTEM_ERROR_PAGE;
    }
    
    
    

}