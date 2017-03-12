package web.controller.identity;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import model.identity.User;
import service.api.identity.IdentityManager;

@RestController
@RequestMapping("/rest/identity/")
public class IdentityRestController
{
    //---------------------------------
    private static final Logger logger = LoggerFactory.getLogger(IdentityRestController.class);
    //---------------------------------
    
    @Autowired
    private IdentityManager identityManager;
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/user/list", 
                     method = {RequestMethod.POST, RequestMethod.GET}, 
                     produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Map<String,Object>> getUserList( @RequestParam(value = "start", defaultValue = "") int start, 
                                                           @RequestParam(value = "length", defaultValue = "") int length, 
                                                           @RequestParam(value = "draw", defaultValue = "") int draw , 
                                                           @RequestParam(value = "search", defaultValue = "") String search) 
    {
        try 
        {
            PageRequest pageable = new PageRequest(start/length, length);
            Page<User> usersPage = identityManager.getUsersByUserNameAndLastName( "", "", pageable); 
            
            Map<String,Object> data = new HashMap<>();
            
            data.put("data", usersPage.getContent());
            data.put("draw", draw);
            data.put("recordsTotal",usersPage.getTotalElements());
            data.put("recordsFiltered",usersPage.getTotalElements());
            
            return new ResponseEntity<Map<String,Object>>(data, HttpStatus.OK);
        }
        catch(Exception e) 
        {
            logger.error( " Error getting user list:", e );
            return new ResponseEntity<Map<String, Object>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }    
    
}
