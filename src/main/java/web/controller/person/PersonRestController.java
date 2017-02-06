package web.controller.person;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.api.person.PersonManager;

@RestController
@RequestMapping("/rest/person")
public class PersonRestController
{
    // ---------------------------------
    private static final Logger logger = LoggerFactory.getLogger( PersonRestController.class );
    // ---------------------------------


    @Autowired
    private PersonManager personManager;
    
    
   
    /*******************************************************
     * 
     */
    @RequestMapping("/code/generate")
    public String generateCode() 
    {
        try 
        {
            return personManager.generateCode();
        }
        catch(Exception e) 
        {
            logger.error( " Person RestController Error:", e );
            return null;
        }
    }    
}
