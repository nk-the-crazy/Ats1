package model.group;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import model.identity.User;

@Entity
@Table( name = "user_group" )
@Access( AccessType.FIELD )
public class UserGroup implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 5991753394548347179L;

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
    private long id;
    
    @Column(name = "name", length = 80, nullable = false, unique = true)
    private String name;
    
    @Column(name = "type")
    private short type = 1;

    @Column(name = "status")
    private short status = 1;
    
    @Column(name = "last_login")
    private Date created = new Date(System.currentTimeMillis());

    @Column(name = "details")
    private String details;
    
    @ManyToMany(mappedBy="groups", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();

    public short getType()
    {
        return type;
    }
    
    public void setId( long id )
    {
        this.id = id;
    }

    public void setType( int type )
    {
        this.type = (short)type;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus( int status )
    {
        this.status = (short)status;
    }

    public Date getCreated()
    {
        return created;
    }

    public void setCreated( Date created )
    {
        this.created = created;
    }

    public long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getDetails()
    {
        return details;
    }

    public void setDetails( String details )
    {
        this.details = details;
    }

    public List<User> getUsers()
    {
        return users;
    }

    public void setUsers( List<User> users )
    {
        this.users = users;
    }
    
    
    public void addUser(User user) 
    {
        this.users.add(user);
        
        if (!user.getGroups().contains(this)) 
        { 
            user.getGroups().add(this);
        }
    }

    
    public void removeUser(User user) 
    {
        if (user.getGroups().contains(this)) 
        { 
            user.getGroups().remove( this);
        }
        
        this.users.remove(user);
    }
    
}
