package web.controller.identity;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
	@RequestMapping( "/main.vw" )
	public String mainView()
	{
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
    public String registerUser( Model model, @ModelAttribute( "user" ) User user,
                                      @RequestParam( name = "organizationId", required = true ) long organizationId,
                                      @RequestParam( name = "roleIds" , required = false ) List<Long> roleIds,
                                      @RequestParam( name = "groupIds", required = false ) List<Long> groupIds)
    {
        try
        {
           
            user = identityManager.saveUser( user, organizationId, roleIds, groupIds );
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
    

}