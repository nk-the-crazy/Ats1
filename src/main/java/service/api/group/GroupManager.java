package service.api.group;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import model.group.UserGroup;
import model.identity.User;


public interface GroupManager
{

    Page<UserGroup> getGroupsByName( String groupName, Pageable pageable );

    UserGroup getGroupDetails( long groupId );

    List<UserGroup> getGroupsByName( String groupName );

    UserGroup createGroup( String name, String details, int type );

    UserGroup saveGroup( UserGroup group );

    Page<User> getGroupUsers( long groupId, Pageable pageable );

    UserGroup getGroupById( long groupId );

    List<UserGroup> getGroupShortListByName( String groupName );

    boolean removeGroup( long groupId );

    void removeUser( long groupId, long userId );

    void addUsers( long groupId, List<Long> userIds );

    List<UserGroup> getGroups();

}
