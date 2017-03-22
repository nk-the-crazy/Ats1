package web.controller.group;


import model.group.UserGroup;
import model.identity.User;
import service.api.group.GroupManager;
import web.view.ModelView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class GroupController
{
    //---------------------------------
    private static final Logger logger = LoggerFactory.getLogger(GroupController.class);
    //---------------------------------

    @Autowired
	private GroupManager groupManager;
    
  
   
    /*******************************************************
     * 
     */
	@RequestMapping( value = "/group_list.vw")
    public ModelAndView getGroups( @RequestParam( name = "groupName" , defaultValue = "", required = false ) 
                                  String groupName, Pageable pageable )
    {
	     ModelAndView model = new ModelAndView( ModelView.VIEW_MAIN_PAGE );
	        
	        try
	        {
	            Page<UserGroup> groupsPage = groupManager.getGroupsByName( groupName,  pageable);
	                    
	            model.addObject( "groupsPage", groupsPage );
	            model.setViewName( ModelView.VIEW_GROUP_LIST_PAGE);
	        }
	        catch(Exception e)
	        {
	            logger.error( " **** Error getting group list:", e );        
	        }
	        
	        return model;
    }
	
	
	/*******************************************************
     * 
     */
    @RequestMapping( value = "/group_details.vw")
    public String getGroupDetails( @RequestParam( "group_id" ) long groupId, Pageable pageable, Model model) 
    {
        try
        {
            UserGroup group = groupManager.getGroupDetails( groupId);
            Page<User> usersPage = groupManager.getGroupUsers( groupId, pageable );
            
            model.addAttribute( "groupDetails", group );
            model.addAttribute( "usersPage", usersPage );
            
            return ModelView.VIEW_GROUP_DETAILS_PAGE;
        }
        catch(Exception e)
        {
            model.addAttribute( "errorMessage", "message.error.system" );
            logger.error( " **** Error getting group Details:", e );        
        }
        
        return ModelView.VIEW_SYSTEM_ERROR_PAGE;
        
    }
    
    
    
    /*******************************************************
     * 
     */
    @RequestMapping("/group_register.vw")
    public String registerGroupView()
    {
        return ModelView.VIEW_GROUP_REGISTER_PAGE;
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/group_register.do")
    public ModelAndView registerGroup( @ModelAttribute( "group" ) UserGroup group)
    {
        ModelAndView model = new ModelAndView( ModelView.VIEW_GROUP_REGISTER_PAGE );
        
        try
        {
            group = groupManager.saveGroup( group );
            
            return new ModelAndView("redirect:group_details.vw?group_id=" + group.getId() );
        }
        catch(IllegalArgumentException e)
        {
            model.addObject( "errorMessage", "message.error.attribute.invalid");
        }
        catch(Exception e)
        {
            logger.error( " **** Error registering group:", e ); 
            model.addObject( "errorMessage", "message.error.system" );
        }
        
        return model;
        
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping("/group_edit.vw")
    public String editGroupView(@RequestParam( "group_id" ) long groupId, Model model)
    {
        model.addAttribute( "groupDetails" , groupManager.getGroupDetails( groupId ));
        
        return ModelView.VIEW_GROUP_EDIT_PAGE;
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/group_edit.do")
    public String editGroup( @ModelAttribute( "group" ) UserGroup group, Model model)
    {

        try
        {
            group = groupManager.saveGroup( group );
            
            return "redirect:group_details.vw?group_id=" + group.getId();
        }
        catch(IllegalArgumentException e)
        {
            model.addAttribute( "errorMessage", "message.error.attribute.invalid");
        }
        catch(Exception e)
        {
            logger.error( " **** Error editing group data:", e ); 
            model.addAttribute( "errorMessage", "message.error.system" );
        }
        
        return editGroupView(group.getId(), model);

    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/group_remove.do")
    public String removeGroup( @RequestParam( "group_id" ) long groupId, Pageable pageable, Model model )
    {

        try
        {
            if(groupManager.removeGroup( groupId ))
            {
                return "redirect:group_list.vw";
            }
            else
            {
                model.addAttribute( "errorMessage", "message.error.remove.has_child" );
            }
        }
        catch(Exception e)
        {
            logger.error( " **** Error removing group data:", e ); 
            model.addAttribute( "errorMessage", "message.error.system" );
        }
        
        return getGroupDetails( groupId, pageable, model);

    }

}