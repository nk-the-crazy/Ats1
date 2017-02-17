package model.person;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import model.contact.Address;
import model.contact.Contact;
import model.identity.User;
import model.organization.Organization;
import model.person.Person;
import model.person.PersonDetails;


@Entity
@Table( name = "person" )
public class Person
{
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	@Column( name = "id")
	private long id;
	
    //@Column( name = "code", nullable = false, unique = true )
    @Column( name = "code")
    private String code;
	
    @Column( name = "first_name", length = 60, nullable = false )
	private String firstName;
	
	@Column( name = "last_name", length = 60, nullable = false )
	private String lastName;
	
	@Column( name = "middle_name", length = 60 )
	private String middleName = "";
	
	@Column( name = "type" )
	private short type = 2;
	
	@Column( name = "status" )
	private short status = 1;    
	
	// *********************************************
    @OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY )
    @JoinColumn(name="details_id")
    private PersonDetails detail; 
    
    // *********************************************
    @OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY )
    @JoinColumn(name="address_id")
    private Address address;
    
    // *********************************************
    @OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY )
    @JoinColumn(name="contact_id")
    private Contact contact;
    
    // *********************************************
    @OneToMany(mappedBy="person", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<User>();
    
    
    // *********************************************
    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;
    // *********************************************

	
	/** *******************************************************************************
	 * 
	 * 
	 */
	
    public long getId()
	{
		return id;
	}

    public String getFirstName()
	{
		return firstName;
	}

	
    public void setFirstName( String firstName )
	{
		this.firstName = firstName;
	}

	
    public String getLastName()
	{
		return lastName;
	}

	
    public void setLastName( String lastName )
	{
		this.lastName = lastName;
	}

	
    public String getMiddleName()
	{
		return middleName;
	}

	
    public void setMiddleName( String middleName )
	{
		this.middleName = middleName;
	}
    
    	
    public String getCode()
    {
        return code;
    }

    public void setCode( String code )
    {
        this.code = code;
    }

    public int getType()
	{
		return type;
	}

	
    public void setType( int type )
	{
		this.type = (short)type;
	}

	
    public short getStatus()
	{
		return status;
	}

	
    public void setStatus( short status )
	{
		this.status = status;
	}

	public PersonDetails getDetail()
    {
        return detail;
    }

    public void setDetail( PersonDetails detail )
    {
        this.detail = detail;
    }

    public Set<User> getUsers()
    {
        return users;
    }

    public void setUsers( Set<User> users )
    {
        this.users = users;
    }
    
    public Organization getOrganization()
    {
        return organization;
    }

    
    public void setOrganization(Organization organization) 
    {
        this.organization = organization;
        
        if (!organization.getPersonList().contains(this)) 
        { 
            organization.getPersonList().add(this);
        }
    }

    public Address getAddress()
    {
        return address;
    }

    public void setAddress( Address address )
    {
        this.address = address;
    }

    public Contact getContact()
    {
        return contact;
    }

    public void setContact( Contact contact )
    {
        this.contact = contact;
    }
    
    
    
   
    
}
