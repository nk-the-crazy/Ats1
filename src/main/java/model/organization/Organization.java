package model.organization;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import model.contact.Address;
import model.contact.Contact;
import model.person.Person;

@Entity
@Table( name = "organization" )
public class Organization
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id")
    private long id;
    
    @Column( name = "code", nullable = false, unique = true )
    private String code;
    
    @Column( name = "name", length = 200, nullable = false )
    private String name;
    
    @Column( name = "type")
    private short type = 2;
    
    @Column( name = "status")
    private short status = 1;
    
    @OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY )
    @JoinColumn(name="details_id")
    private OrganizationDetails details;
    
    @OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY )
    @JoinColumn(name="address_id")
    private Address address;
    
    @OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY )
    @JoinColumn(name="contact_id")
    private Contact contact;

    @OneToMany(mappedBy="organization", fetch = FetchType.LAZY)
    private Set<Person> personList = new HashSet<>();

    public String getCode()
    {
        return code;
    }

    public void setCode( String code )
    {
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public OrganizationDetails getDetails()
    {
        return details;
    }

    public void setDetails( OrganizationDetails details )
    {
        this.details = details;
    }

     public long getId()
    {
        return id;
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

    public void setStatus( int status )
    {
        this.status = (short)status;
    } 
    
    
    public Set<Person> getPersonList()
    {
        return personList;
    }

    public void setPersonList( Set<Person> personList )
    {
        this.personList = personList;
    }

    public void addPerson(Person person) 
    {
        this.personList.add(person);
        
        if (person.getOrganization() != this)
        {
            person.setOrganization( this);
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
