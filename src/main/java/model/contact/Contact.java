package model.contact;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "contact" )
public class Contact
{
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "id")
	private long id;
	
	@Column( name = "type")
	private int type = 1;
	
	@Column( name = "phone", length = 20)
	private String phone;
	
	@Column( name = "email", length = 120)
	private String email;
	
    @Column( name = "secondary_contacts", length = 220)
    private String secondaryContacts;
	

	/*********************************************************************************
	 * 
	 **/
	public long getId()
	{
		return id;
	}

	public void setId( long id )
	{
		this.id = id;
	}

	
    public int getType()
	{
		return type;
	}

	
    public void setType( int type )
	{
		this.type = type;
	}
	
    public String getPhone()
	{
		return phone;
	}

	
    public void setPhone( String phone )
	{
		this.phone = phone;
	}

	
    public String getEmail()
	{
		return email;
	}

	
    public void setEmail( String email )
	{
		this.email = email;
	}

    public String getSecondaryContacts()
    {
        return secondaryContacts;
    }

    public void setSecondaryContacts( String secondaryContacts )
    {
        this.secondaryContacts = secondaryContacts;
    }
    
	
}
