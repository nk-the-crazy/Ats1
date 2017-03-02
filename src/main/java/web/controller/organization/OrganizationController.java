package web.controller.organization;

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

import model.organization.Organization;
import model.person.Person;
import service.api.organization.OrganizationManager;
import web.controller.organization.OrganizationController;
import web.view.ModelView;

@Controller
public class OrganizationController
{
    //---------------------------------
    private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);
    //---------------------------------

    @Autowired
    private OrganizationManager organizationManager;
    
  
   
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/organization_list.vw")
    public ModelAndView getOrganizations( @RequestParam( name = "organizationName" , defaultValue = "", required = false ) 
                                  String organizationName, Pageable pageable )
    {
         ModelAndView model = new ModelAndView( ModelView.VIEW_MAIN_PAGE );
            
            try
            {
                Page<Organization> organizationsPage = organizationManager.getOrganizationsByName( organizationName,  pageable);
                        
                model.addObject( "organizationsPage", organizationsPage );
                model.setViewName( ModelView.VIEW_ORGANIZATION_LIST_PAGE);
            }
            catch(Exception e)
            {
                logger.error( " **** Error getting Organization list:", e );        
            }
            
            return model;
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/organization_details.vw")
    public ModelAndView getOrganizationDetails(@RequestParam( "organization_id" ) long organizationId, Pageable pageable)
    {
        ModelAndView model = new ModelAndView( ModelView.VIEW_MAIN_PAGE );
        
        try
        {
            Organization organization = organizationManager.getOrganizationDetails( organizationId );
            Page<Person> personListPage = organizationManager.getOrganizationPersonList( organizationId, pageable );            
            
            model.addObject( "organizationDetails", organization );
            model.addObject( "personListPage", personListPage );
            model.setViewName( ModelView.VIEW_ORGANIZATION_DETAILS_PAGE);
        }
        catch(Exception e)
        {
            logger.error( " **** Error getting Organization Details:", e );        
        }
        
        return model;
        
    }
   
    
    /*******************************************************
     * 
     */
    @RequestMapping("/organization_register.vw")
    public String registerOrganizationView()
    {
        return ModelView.VIEW_ORGANIZATION_REGISTER_PAGE;
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/organization_register.do")
    public ModelAndView registerOrganization( @ModelAttribute( "organization" ) Organization organization )
    {
        ModelAndView model = new ModelAndView( ModelView.VIEW_ORGANIZATION_REGISTER_PAGE );
        
        try
        {
            organization = organizationManager.saveOrganization( organization ); 
            
            return new ModelAndView("redirect:organization_details.vw?organization_id=" + organization.getId() );
        }
        catch(IllegalArgumentException e)
        {
            model.addObject( "errorMessage", "message.error.attribute.invalid");
        }
        catch(Exception e)
        {
            logger.error( " **** Error registering Organization:", e ); 
            model.addObject( "errorMessage", "message.error.system" );
        }
        
        return model;
        
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping("/organization_edit.vw")
    public String editOrganizationView(@RequestParam( "organization_id" ) long organizationId, Model model)
    {
        model.addAttribute( "organizationDetails" , organizationManager.getOrganizationDetails( organizationId ));
        return ModelView.VIEW_ORGANIZATION_EDIT_PAGE;
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/organization_edit.do")
    public String editOrganization( @ModelAttribute( "group" ) Organization organization, Model model)
    {

        try
        {
            organization = organizationManager.saveOrganization( organization );
            
            return "redirect:organization_details.vw?organization_id=" + organization.getId();
        }
        catch(IllegalArgumentException e)
        {
            model.addAttribute( "errorMessage", "message.error.attribute.invalid");
        }
        catch(Exception e)
        {
            logger.error( " **** Error editing organization data:", e ); 
            model.addAttribute( "errorMessage", "message.error.system" );
        }
        
        return editOrganizationView(organization.getId(), model);

    }

   
    
}
