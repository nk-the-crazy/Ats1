package web.controller.person;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import model.person.Person;
import service.api.person.PersonManager;
import web.common.view.ModelView;

@Controller
public class PersonController
{
    //---------------------------------
    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
    //---------------------------------
    
    
    @Autowired
    private PersonManager personManager;
    
    

    /*******************************************************
     * 
     */
    @RequestMapping( value = "/person_list.vw")
    public ModelAndView getPersonList( @RequestParam( name = "firstName" , defaultValue = "", required = false)
                                       String firstName,
                                       @RequestParam( name = "lastName" , defaultValue = "", required = false ) 
                                       String lastName, Pageable pageable )
    {
         ModelAndView model = new ModelAndView( ModelView.VIEW_MAIN_PAGE );
            
            try
            {
                Page<Person> personsPage = personManager.getPersonListByFirstNameAndLastName( firstName,lastName,  pageable);
                        
                model.addObject( "groupsPage", personsPage );
                model.setViewName( ModelView.VIEW_PERSON_LIST_PAGE);
            }
            catch(Exception e)
            {
                logger.error( " **** Error getting person list:", e );        
            }
            
            return model;
    }
    
}
