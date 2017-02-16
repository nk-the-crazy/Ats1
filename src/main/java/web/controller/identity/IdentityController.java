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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import common.exceptions.security.InvalidLoginException;
import common.exceptions.security.InvalidPasswordException;
import common.utils.StringUtils;
import web.common.view.ModelView;


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
	@RequestMapping( "/main.vw" )
	public String mainView()
	{
		return ModelView.VIEW_MAIN_PAGE;
	}
	
	
	/*******************************************************
     * 
     * */
    @RequestMapping( value = "/login.do", method = RequestMethod.POST )
    public ModelAndView loginUser( @RequestParam( "userName" ) String userName, 
                                   @RequestParam( "password" ) String password, HttpSession session )
    {
        ModelAndView model = new ModelAndView( ModelView.VIEW_MAIN_PAGE );
        
        try
        {
            SessionData sData = identityManager.loginUser( userName, password );
            
            if ( sData != null )
            {
                session.setAttribute( "sessionData", sData );
               return new ModelAndView("redirect:main.vw");
            }
            else
            {
                throw new InvalidLoginException("Invalid Login");
            }
            
        }
        catch(InvalidLoginException e)
        {
            logger.error( " **** Invalid Login ", e );        
            model.addObject( "errorMessage", "message.error.invalid_login");
            model.setViewName( ModelView.VIEW_LOGIN_PAGE);
        }
        catch(Exception e)
        {
            logger.error( " **** Error authenticating user", e );        
            model.addObject( "loginMessage", "message.error.invalid_login");
            model.setViewName( ModelView.VIEW_LOGIN_PAGE);
        }
        
        return model;
        
    }
    

    /*******************************************************
     * 
     */
    @RequestMapping( value = "/edit_password.vw")
    public ModelAndView changePasswordView()
    {
        ModelAndView model = new ModelAndView( ModelView.VIEW_MAIN_PAGE );
        
        try
        {
            model.setViewName( ModelView.VIEW_EDIT_PASSWORD_PAGE);
            
        }
        catch(Exception e)
        {
            logger.error( " **** Error changing user password:", e );        
        }
        
        return model;
        
    }
    
    
    /*******************************************************
     * 
     * */
    @RequestMapping( value = "/edit_password.do", method = RequestMethod.POST )
    public ModelAndView changeUserPassword( @RequestParam( "currentPassword" ) String currentPassword,
                                            @RequestParam( "newPassword" ) String newPassword, HttpSession session)
    {
        ModelAndView model = new ModelAndView( ModelView.VIEW_MAIN_PAGE );
        
        try
        {
            SessionData sData = (SessionData)session.getAttribute( "sessionData" );
            
            if(sData == null)
            {
                model.setViewName( ModelView.VIEW_EDIT_PASSWORD_PAGE);
            }
            else
            {
                if(identityManager.changeUserPassword( sData.getUser(), currentPassword, newPassword ))
                {
                    model.setViewName( ModelView.VIEW_MAIN_PAGE);
                }
                else
                {
                    model.setViewName( ModelView.VIEW_EDIT_PASSWORD_PAGE);
                }
            }
        }
        catch(Exception e)
        {
            logger.error( " **** Error changing password:", e );        
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
	@RequestMapping( value = "/logout.do", method = RequestMethod.GET )
	public String logoutUser( HttpSession session )
	{
		session.removeAttribute( "sessionData" );
		return ModelView.VIEW_LOGIN_PAGE;
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
    @RequestMapping( value = "/role_register.do")
    public ModelAndView registerRoleView( @ModelAttribute( "role" ) Role role)
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
            logger.error( " **** Error registering role:", e ); 
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
    public ModelAndView registerUserView( @ModelAttribute( "user" ) User user,
                                          @RequestParam( name = "organizationId", required = true ) long personId,
                                          @RequestParam( name = "roleIds" , required = false ) List<Long> roleIds,
                                          @RequestParam( name = "groupIds", required = false ) List<Long> groupIds)
    {
        ModelAndView model = new ModelAndView( ModelView.VIEW_USER_REGISTER_PAGE );
        
        try
        {
            user = identityManager.saveUser( user );
            
            return new ModelAndView("redirect:user_details.vw?user_id=" + user.getId() );
        }
        catch(InvalidPasswordException e)
        {
            model.addObject( "errorMessage", "message.error.password.invalid");
        }
        catch(IllegalArgumentException e)
        {
            model.addObject( "errorMessage", "message.error.attribute.invalid");
        }
        catch(Exception e)
        {
            logger.error( " **** Error registering role:", e ); 
            model.addObject( "errorMessage", "message.error.system" );
        }
        
        return model;
        
    }
    

}