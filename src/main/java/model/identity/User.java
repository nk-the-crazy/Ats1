package model.identity;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import model.group.UserGroup;
import model.person.Person;


@Entity
@Table(name = "userl")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "user_name", length = 80, nullable = false, unique = true)
    private String userName;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name = "salt", length = 100, nullable = false)
    private String salt;

    @Column(name = "type")
    private short type = 2;

    @Column(name = "status")
    private short status = 1;

    @Column(name = "last_login")
    private Date lastLogin = null;
    
    @Column(name = "email", nullable = false )
    private String email;

    // *********************************************
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
                    @JoinColumn(name = "role_id", referencedColumnName = "id") })
    private List<Role> roles = new ArrayList<>();
    // *********************************************

    // *********************************************
    @ManyToOne( cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;
    // *********************************************

    // *********************************************
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_groups", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
                    @JoinColumn(name = "group_id", referencedColumnName = "id") })
    private List<UserGroup> groups = new ArrayList<>();
    // *********************************************

    
    public long getId()
    {
        return id;
    }

    public void setId( long id )
    {
        this.id = id;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName( String userName )
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public String getSalt()
    {
        return salt;
    }

    public void setSalt( String salt )
    {
        this.salt = salt;
    }

    public int getType()
    {
        return type;
    }

    public void setType( int type )
    {
        this.type = (short)type;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus( short status )
    {
        this.status = status;
    }

    public Date getLastLogin()
    {
        return lastLogin;
    }

    public void setLastLogin( Date lastLogin )
    {
        this.lastLogin = lastLogin;
    }
    
    public String getEmail()
    {
        return email;
    }

    public void setEmail( String email )
    {
        this.email = email;
    }

    public List<Role> getRoles()
    {
        return roles;
    }

    public void setRoles( List<Role> roles )
    {
        this.roles = roles;
    }

    public Person getPerson()
    {
        return person;
    }

    public void setPerson( Person person )
    {
        this.person = person;
        
        person.getUsers().add( this );
    }
    
    public List<UserGroup> getGroups()
    {
        return groups;
    }

    public void setGroups( List<UserGroup> groups )
    {
        this.groups = groups;
    }

    public void addRole( Role role )
    {
        roles.add( role );
    }
    
    public void addRole( UserGroup group )
    {
        groups.add( group );
    }
    
    public void addGroup(UserGroup group) 
    {
        this.groups.add(group);
        
        if (!group.getUsers().contains(this)) 
        { 
            group.getUsers().add(this);
        }
    }
    
    
    public List<GrantedAuthority> getAuthorities()
    {
        List<GrantedAuthority> roleList = new ArrayList<>();
        
        for(Role role:roles)
        {
            roleList. addAll( role.getPermissionsAsString() );
        }
        
        return roleList; 
    }
    


}
