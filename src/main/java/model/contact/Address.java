package model.contact;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "address" )
public class Address
{
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Column( name = "id")
	private long id;
	
	@Column( name = "country_id")
	private short countryId = 2;
	
	@Column( name = "region_id")
	private short regionId = 2;
	
	@Column( name = "city", length = 120)
	private String city;
	
	@Column( name = "address_line", length = 250)
	private String addressLine;

	@Column( name = "secondary_address", length = 250)
	private String secondaryAddress;
	
	@Column( name = "type")
    private short type = 1;
    
	
	/*********************************************************************************
	 * 
	 * 
	 **/
	public long getId()
	{
		return id;
	}

	
    public int getCountryId()
	{
		return countryId;
	}

	
    public void setCountryId( int countryId )
	{
		this.countryId = (short) countryId;
	}

	
    public int getRegionId()
	{
		return regionId;
	}

	
    public void setRegionId( int regionId )
	{
		this.regionId = (short)regionId;
	}

	
    public String getCity()
	{
		return city;
	}

	
    public void setCity( String city )
	{
		this.city = city;
	}

	
    public String getAddressLine()	
	{
		return addressLine;
	}


	
    public void setAddressLine( String addressLine )
	{
		this.addressLine = addressLine;
	}		

	
    public String getSecondaryAddress()
    {
        return secondaryAddress;
    }


    public void setSecondaryAddress( String secondaryAddress )
    {
        this.secondaryAddress = secondaryAddress;
    }


    public int getType()
    {
        return type;
    }


    public void setType( int type )
    {
        this.type = (short)type;
    }


}
