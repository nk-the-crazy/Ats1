package web.controller.identity;


import javax.servlet.http.HttpSession;
import model.common.session.SessionData;
import model.group.UserGroup;
import model.identity.User;
import service.api.identity.IdentityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import common.exceptions.security.InvalidLoginException;
import web.common.view.ModelView;


@Controller
public class IdentityController
{
    //---------------------------------
    private static final Logger logger = LoggerFactory.getLogger(IdentityController.class);
    //---------------------------------

    @Autowired
	private IdentityManager identityManager;
    
  
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
                model.setViewName( ModelView.VIEW_MAIN_PAGE);
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
	@RequestMapping( value = "/logout.do", method = RequestMethod.GET )
	public String logoutUser( HttpSession session )
	{
		session.removeAttribute( "sessionData" );
		return ModelView.VIEW_LOGIN_PAGE;
	}
	
	

}