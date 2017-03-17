package web.controller.group;

import java.util.HashMap;
import java.util.List;
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
import service.api.group.GroupManager;


@RestController
@RequestMapping("/rest/group")
public class GroupRestController
{
    // ---------------------------------
    private static final Logger logger = LoggerFactory.getLogger( GroupRestController.class );
    // ---------------------------------


    @Autowired
    private GroupManager groupManager;
    
    
    /*******************************************************
     * 
     */
    @RequestMapping( value = "/user/list", 
                     method = {RequestMethod.POST, RequestMethod.GET}, 
                     produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Map<String,Object>> getUserList( @RequestParam(value = "group_id", defaultValue = "") long groupId, 
                                                           @RequestParam(value = "start", defaultValue = "") int start, 
                                                           @RequestParam(value = "length", defaultValue = "") int length, 
                                                           @RequestParam(value = "draw", defaultValue = "") int draw , 
                                                           @RequestParam(value = "search", defaultValue = "") String search) 
    {
        try 
        {
            PageRequest pageable = new PageRequest(start/length, length);
            Page<User> usersPage = groupManager.getGroupUsers( groupId, pageable); 
            
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
    
    /*******************************************************
     * 
     */
    @RequestMapping(value = "/user/remove",
                    method = RequestMethod.GET, 
                    produces = MediaType.TEXT_PLAIN_VALUE)
    public String removeUserFromGroup(@RequestParam(value = "group_id") long groupId,
                                      @RequestParam(value = "user_id") long userId) 
    {
        try 
        {
            groupManager.removeUser(groupId , userId);
            return "";
        }
        catch(Exception e) 
        {
            logger.error( " Error removing user from group:", e );
            return null;
        }
    }
    
    
    /*******************************************************
     * 
     */
    @RequestMapping(value = "/user/add",
                    method = RequestMethod.POST, 
                    produces = MediaType.TEXT_PLAIN_VALUE)
    public String addUsersToGroup(@RequestParam(value = "group_id") long groupId,
                                  @RequestParam(value = "userIds") List<Long> userIds) 
    {
        try 
        {
            groupManager.addUsers(groupId , userIds);
            return "";
        }
        catch(Exception e) 
        {
            logger.error( " Error removing user from group:", e );
            return null;
        }
    }
}
