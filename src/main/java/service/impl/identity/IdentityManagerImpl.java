package service.impl.identity;

import java.security.AccessControlException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;

import common.exceptions.security.InvalidLoginException;
import common.exceptions.security.InvalidPasswordException;
import common.exceptions.security.SystemSecurityException;
import common.utils.security.SecurityUtils;
import dao.api.group.GroupDAO;
import dao.api.identity.PermissionDAO;
import dao.api.identity.RoleDAO;
import dao.api.identity.UserDAO;
import model.common.session.SessionData;
import model.group.UserGroup;
import model.identity.Permission;
import model.identity.Role;
import model.identity.User;
import model.identity.UserType;
import service.api.identity.IdentityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service("userManagerService")
@Transactional
public class IdentityManagerImpl implements IdentityManager
{
    
    //---------------------------------
    private static final Logger logger = LoggerFactory.getLogger(IdentityManagerImpl.class);
    //---------------------------------
            
    @Autowired
    UserDAO userDAO;

    @Autowired
    GroupDAO groupDAO;

    @Autowired
    RoleDAO roleDAO;

    @Autowired
    PermissionDAO permissionDAO;

  
    /**************************************************
     * 
     */
    @Override
    public User auhtenticateUser( String userName, String password ) throws SystemSecurityException
    {
        User user = userDAO.findByUserName( userName );

        if ( user != null )
        {
            String pswHash = "";
            try
            {
                pswHash = SecurityUtils.generateSecurePassword( password, user.getSalt() );
            }
            catch ( NoSuchAlgorithmException e )
            {
                /* ignore */ 
            }

            if ( !pswHash.equals( user.getPassword() ) )
            {
                throw new InvalidLoginException( "***** Invalid Login for user:" + userName );
            }
        }
        else
        {
            throw new InvalidLoginException();
        }

        return user;
    }


    /**************************************************
     * 
     */
    @Override
    public User auhtenticateByToken( String token ) throws SecurityException
    {
        return null;
    }


    /**************************************************
     * 
     */
    @Override
    public SessionData loginUser( String userName , String password  ) throws SecurityException
    {
        SessionData sessionData = null;
        User user = null;
        
        
        if("token".equals( userName ))
        {
            user = auhtenticateByToken(password);
        }
        else
        {
            user = auhtenticateUser( userName, password );
        }
        
        if(user != null)
        {
            sessionData = new SessionData(user);
            user.setLastLogin( new Date(System.currentTimeMillis()) );
            userDAO.save( user );
            
            return sessionData;
        }
        else
        {
            return null;
        }
        
    }
    
    
    /**************************************************
     * 
     */
    @Override
    public User createUser(String userName, String password, String email, int type)
    {
        User user = null;
        
        isValidUserName( userName );
        isValidPassword( userName, password );
        
        try 
        {
            user = new User();
            user.setUserName( userName );
            user.setPassword( password );
            user.setType( type );
            user.setEmail( email );
        }
        catch(Exception e) 
        {
            
        }
        
        return user;
    
    }
    
    
    /**************************************************
     * 
     */
    @Override
    public Role createRole(String roleName, String details, int type)
    {
        Role role = null;
        
        isValidRoleName( roleName );
        
        try 
        {
            role = new Role();
            role.setName( roleName );
            role.setDetails( details );
            role.setType( type );
        }
        catch(Exception e) 
        {
            
        }
        
        return role;
    
    }
    
   

    /**************************************************
     * 
     */
    @Override
    public Permission createPermission(int item, boolean read, boolean write, 
                                                 boolean update, boolean delete)
    {
        Permission permission = null;
        
        try 
        {
            permission = new Permission();
            permission.setItem( item );
            permission.setRead( read );
            permission.setWrite( write );
            permission.setUpdate( update );
            permission.setDelete( delete );
        }
        catch(Exception e) 
        {
        }
        
        return permission;
    
    }
    

    /**************************************************
     * 
     */
    @Override
    public Role saveRole( Role role )
    {
       
        isValidRoleName( role.getName() );

        try
        {
            return roleDAO.save( role );
        }
        catch ( Exception e )
        {
            logger.error( " **** Error in saving role:", e );        
        }

        return null;
    }

    /**************************************************
     * 
     */
    @Override
    public Permission savePermission( Permission permission )
    {
        try
        {
            return permissionDAO.save( permission );
        }
        catch ( Exception e )
        {
            logger.error( " **** Error in saving Permission:", e );        
        }

        return null;
    }


    /**************************************************
     * 
     */
    @Override
    public User saveUser( User user )
    {
        // *********************************
        if ( Strings.isNullOrEmpty( user.getUserName() ) )
        {
            user.setUserName( Integer.toString( SecurityUtils.generateShortRandom() ));
        }
        if ( Strings.isNullOrEmpty( user.getPassword() ) )
        {
            user.setPassword( Integer.toString( SecurityUtils.generateShortRandom() ));
        }
        // *********************************

        isValidUserName( user.getUserName() );
        isValidPassword( user.getUserName(), user.getPassword() );

        try
        {
            String salt = SecurityUtils.generateSecureRandom();
            user.setPassword( SecurityUtils.generateSecurePassword( user.getPassword(), salt ));
            user.setSalt( salt );
            
            return userDAO.save( user );
        }
        catch ( Exception e )
        {
            logger.error( " **** Error in Adduser", e );        
        }

        return null;
    }


    /* *************************************************
     */
    @Override
    public Page<User> getUsers(Pageable pageable)
    {
        return userDAO.findAll( pageable );
    }


    /* *************************************************
     */
    @Override
    public User getUser(long userId)
    {
        return userDAO.findOne( userId );
    }


    /* *************************************************
     */
    @Override
    public List<User> getUsers()
    {
        return userDAO.findAll();
    }
    
    
    
    /* *************************************************
     */
    @Override
    public Page<User> getUsersByUserNameAndLastName(String userName, String lastName, Pageable pageable)
    {
        return userDAO.findByUserNameAndLastName( userName, lastName, pageable);
    }

    
    /* *************************************************
     */
    @Override
    public List<User> getUsersByUserNameAndLastName(String userName, String lastName)
    {
        return userDAO.findByUserNameAndLastName( userName, lastName);
    }

    
    /**************************************************
     * 
     */
    @Override
    public User getUserFullDetails( long userId )
    {
        // *********************************
        try
        {
            User userDetails = userDAO.getFullDetails( userId );
            
            if(userDetails != null)
            {
                return userDetails;
            }
        }
        catch ( Exception e )
        {
            logger.error( " **** Error in get User Full Details", e );        
        }

        return null;
    }

    
    /**************************************************
     * 
     */
    @Override
    public User getUserDetails( long userId )
    {
        // *********************************
        try
        {
            User userDetails = userDAO.getDetails( userId );
            
            if(userDetails != null)
            {
                return userDetails;
            }
        }
        catch ( Exception e )
        {
            logger.error( " **** Error in get User Details", e );        
        }

        return null;
    }
    

    /**************************************************
     * 
     */
    @Override
    public Page<UserGroup> getUserGroups( long userId, Pageable pageable )
    {
        // *********************************
        try
        {
            return groupDAO.getByUserId( userId, pageable );
        }
        catch ( Exception e )
        {
            logger.error( " **** Error in get User Groups", e );        
        }

        return null;
    }
    
    
    /**************************************************
     * 
     */
    @Override
    public Page<Role> getRolesByRoleName( String roleName, Pageable pageable )
    {
        return roleDAO.findByRoleName( roleName, pageable );
    }

    /**************************************************
     * 
     */
    @Override
    public List<Role> getRoleShortListByRoleName( String roleName)
    {
        return roleDAO.getShortListByRoleName( roleName );
    }

    
    /**************************************************
     * 
     */
    @Override
    public Role getRoleDetails( long roleId )
    {
        // *********************************
        try
        {
            return roleDAO.findById( roleId );
        }
        catch ( Exception e )
        {
            logger.error( " **** Error in get role details:", e );        
        }

        return null;
    }


    /**************************************************
     * 
     */
    @Override
    public Page<User> getRoleUsers( long roleId, Pageable pageable )
    {
        // *********************************
        try
        {
            return userDAO.getByRoleId( roleId, pageable );
        }
        catch ( Exception e )
        {
            logger.error( " **** Error in get role details:", e );        
        }

        return null;
    }


    
    /* *************************************************
     */
    @Override
    public boolean changeUserPassword( long userId, String oldPassword, String newPassword ) throws Exception
    {
        User user = null;//identityDAO.find( userId );      
        return changeUserPassword( user, oldPassword, newPassword );
    }


    /* *************************************************
     */
    @Override
    public boolean changeUserPassword( User user, String oldPassword, String newPassword ) throws Exception
    {
        String salt;

        if ( oldPassword.equals( newPassword ) )
        {
            throw new IllegalArgumentException( "NewPassword cannot be the same as old one." );
        }

        //******Cannot update Internal User *************
        if ( user.getType() == UserType.System.getId() )
        {
            throw new AccessControlException( "Internal User cannot be updated" );
        }

        String pswHash = SecurityUtils.generateSecurePassword( oldPassword, user.getSalt() );

        if ( !pswHash.equals( user.getPassword() ) )
        {
            throw new InvalidLoginException( "Invalid old password specified" );
        }

        isValidPassword( user.getUserName(), newPassword );

        try
        {
            salt = SecurityUtils.generateSecureRandom();
            newPassword = SecurityUtils.generateSecurePassword( newPassword, salt );
            user.setSalt( salt );
            user.setPassword( newPassword );
            //identityDAO.merge( user );
        }
        catch ( NoSuchAlgorithmException | NoSuchProviderException e )
        {
            throw new Exception( "Internal error" );
        }

        return true;
    }


  
    /**************************************************
     * 
     */
    private void isValidRoleName( String roleName )
    {
        if ( Strings.isNullOrEmpty( roleName ) || roleName.length() < 4 )
        {
            throw new IllegalArgumentException( "Role name cannot be shorter than 4 characters." );
        }
        if ( roleName.equalsIgnoreCase( "token" ) || roleName.equalsIgnoreCase( "role" )
                || roleName.equalsIgnoreCase( "system" ) )
        {
            throw new IllegalArgumentException( "Role name is reserved by the system." );
        }
    }
    

    /**************************************************
     * 
     */
    private void isValidUserName( String userName )
    {
        if ( Strings.isNullOrEmpty( userName ) || userName.length() < 4 )
        {
            throw new IllegalArgumentException( "User name cannot be shorter than 4 characters." );
        }

        if ( userName.equalsIgnoreCase( "token" ) || userName.equalsIgnoreCase( "administrator" )
                || userName.equalsIgnoreCase( "system" ) )
        {
            throw new IllegalArgumentException( "User name is reserved by the system." );
        }
    }


    /**************************************************
     * 
     */
    private void isValidPassword( String userName, String password )
    {
        if ( Strings.isNullOrEmpty( password ) || password.length() < 4 )
        {
            throw new InvalidPasswordException( "Password cannot be shorter than 4 characters" );
        }

        if ( password.equalsIgnoreCase( userName ) || password.equalsIgnoreCase( "password" )
                || password.equalsIgnoreCase( "system" ) )
        {
            throw new InvalidPasswordException( "Password doesn't match security requirements" );
        }
    }


}
