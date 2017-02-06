package service.api.identity;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import common.exceptions.security.SystemSecurityException;
import model.common.session.SessionData;
import model.group.UserGroup;
import model.identity.User;

public interface IdentityManager
{

    boolean changeUserPassword( long userId, String oldPassword, String newPassword ) throws Exception;

    boolean changeUserPassword( User user, String oldPassword, String newPassword ) throws Exception;

    Page<User> getUsers( Pageable pageable );

    User saveUser( User user );

    List<User> getUsers();

    Page<User> getUsersByUserNameAndLastName( String userName, String lastName, Pageable pageable );

    List<User> getUsersByUserNameAndLastName( String userName, String lastName );

    User createUser( String userName, String password, String email, int type );

    User auhtenticateUser( String userName, String password ) throws SystemSecurityException;

    User auhtenticateByToken( String token ) throws SecurityException;

    SessionData loginUser( String userName, String password ) throws SecurityException;

    User getUserFullDetails( long userId );

    User getUserDetails( long userId );

    Page<UserGroup> getUserGroups( long userId, Pageable pageable );

}
