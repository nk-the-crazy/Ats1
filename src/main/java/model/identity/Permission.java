package model.identity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


/**
 *
 */
@Entity
@Table( name = "permission" )
@Access( AccessType.FIELD )
public class Permission implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 6237190276035909858L;

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
    private long id;

    @Column( name = "item" )
    private short item;

    @Column( name = "read" )
    private boolean read = false;

    @Column( name = "write" )
    private boolean write = false;

    @Column( name = "update" )
    private boolean update = false;

    @Column( name = "delete" )
    private boolean delete = false;

    public Long getId()
    {
        return id;
    }
    
    public void setId( final Long id )
    {
        this.id = id;
    }

    
    public boolean isRead()
    {
        return read;
    }

    
    public void setRead( boolean read )
    {
        this.read = read;
    }

  
    public boolean isWrite()
    {
        return write;
    }

    
    public void setWrite( boolean write )
    {
        this.write = write;
    }


    public boolean isUpdate()
    {
        return update;
    }

    
    public void setUpdate( boolean update )
    {
        this.update = update;
    }

    
    public boolean isDelete()
    {
        return delete;
    }

    
    public void setDelete( boolean delete )
    {
        this.delete = delete;
    }

    
    public String getObjectName()
    {
        return "";
    }

    
    public int getItem()
    {
        return item;
    }

    public void setItem( int item )
    {
        this.item = (short)item;
    }

    public List<GrantedAuthority> asString()
    {
        List<GrantedAuthority> perms = new ArrayList<>();
        perms.add(new SimpleGrantedAuthority(PermissionItem.values()[this.item-1].name()));
        return perms;
    }
}