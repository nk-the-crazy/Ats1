package model.identity;

import java.util.ArrayList;
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
import javax.persistence.Table;

@Entity
@Table( name = "role" )
public class Role
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
    private long id;
    
    @Column( name = "name" , nullable = false)
    private String name;

    @Column( name = "type" )
    private short type = 2;
    
    @Column( name = "details")
    private String details;

    
    //*********************************************
    @ManyToMany( fetch = FetchType.EAGER, cascade = CascadeType.ALL )
    @JoinTable( name = "role_permissions",
            joinColumns = { @JoinColumn( name = "role_id", referencedColumnName = "id" ) },
            inverseJoinColumns = { @JoinColumn( name = "perm_id", referencedColumnName = "id" ) } )
    private List<Permission> permissions = new ArrayList<>();
    //*********************************************
    

    public long getId()
    {
        return id;
    }

    public void setId( long id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public int getType()
    {
        return type;
    }

    public void setType( int type )
    {
        this.type = (short)type;
    }

    public List<Permission> getPermissions()
    {
        return permissions;
    }

    public void setPermissions( List<Permission> permissions )
    {
        this.permissions = permissions;
    }

    public void addPermission( Permission perm )
    {
       permissions.add( perm );
    }

    public String getDetails()
    {
        return details;
    }

    public void setDetails( String details )
    {
        this.details = details;
    }
    
    
    
   
}
