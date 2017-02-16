package service.impl.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;

import dao.api.group.GroupDAO;
import dao.api.identity.UserDAO;
import model.group.UserGroup;
import model.identity.User;
import service.api.group.GroupManager;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service("groupManagerService")
@Transactional
public class GroupManagerImpl implements GroupManager
{
    
    //---------------------------------
    private static final Logger logger = LoggerFactory.getLogger(GroupManagerImpl.class);
    //---------------------------------
            
    @Autowired
    GroupDAO groupDAO;

    @Autowired
    UserDAO userDAO;

    
    /**************************************************
     * 
     */
    @Override
    public UserGroup createGroup(String name, String details, int type)
    {
        UserGroup group = null;
        
        isValidGroupName( name );
        
        try 
        {
            group = new UserGroup();
            group.setName( name );
            group.setDetails( details );
            group.setType( type );
        }
        catch(Exception e) 
        {
            
        }
        
        return group;
    }
    
    
    /**************************************************
     * 
     */
    @Override
    public UserGroup saveGroup( UserGroup group)
    {
        isValidGroupName( group.getName() );

        try
        {
            return groupDAO.save( group );
        }
        catch ( Exception e )
        {
            logger.error( " **** Error in save Group", e );        
        }

        return null;
    }
    

    /**************************************************
     * 
     */
    @Override
    public UserGroup getGroupById( long groupId)
    {
        return groupDAO.findOne( groupId ) ;
    }
    

    /**************************************************
     * 
     */
    @Override
    public List<UserGroup> getGroupsByName( String groupName )
    {
        return groupDAO.findByName(groupName);
    }
    

    /**************************************************
     * 
     */
    @Override
    public List<UserGroup> getGroupShortListByName( String groupName )
    {
        return groupDAO.getShortListByGroupName( groupName );
    }
    
    /**************************************************
     * 
     */
    @Override
    public Page<UserGroup> getGroupsByName( String groupName, Pageable pageable )
    {
        return groupDAO.findByName(groupName, pageable);
    }
    
    
    /**************************************************
     * 
     */
    @Override
    public Page<User> getGroupUsers( long groupId, Pageable pageable )
    {
        // *********************************
        try
        {
            return userDAO.getByGroupId( groupId, pageable );
        }
        catch ( Exception e )
        {
            logger.error( " **** Error in get Group Users", e );        
        }

        return null;
    }

    

    /**************************************************
     * 
     */
    @Override
    public UserGroup getGroupDetails( long groupId )
    {
        return groupDAO.getFullDetails( groupId );
    }
    

    /**************************************************
     * 
     */
    private void isValidGroupName( String groupName )
    {
        if ( Strings.isNullOrEmpty( groupName ) || groupName.length() < 4 )
        {
            throw new IllegalArgumentException( "Group name cannot be shorter than 4 characters." );
        }

        if ( groupName.equalsIgnoreCase( "group" ) || groupName.equalsIgnoreCase( "all" )
                || groupName.equalsIgnoreCase( "system" ) )
        {
            throw new IllegalArgumentException( "GroupName is reserved by the system." );
        }
    }

  
    

}
