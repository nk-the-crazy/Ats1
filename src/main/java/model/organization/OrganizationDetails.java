package model.organization;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "organization_details" )
public class OrganizationDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @Column(name = "sector")
    private short sector = 1;
    
    @Column(name = "activity")
    private short activity = 1;
    
    @Column(name = "tax_payer_number") // Tax payer Number
    private String taxPayerNumber = "";

    @Column(name = "registration_number") 
    private String registrationNumber = "";

    @Column(name = "details") 
    private String details = "";

    
    public String getTaxPayerNumber()
    {
        return taxPayerNumber;
    }


    
    public void setTaxPayerNumber( String taxPayerNumber )
    {
        this.taxPayerNumber = taxPayerNumber;
    }


    
    public String getRegistrationNumber()
    {
        return registrationNumber;
    }



    public void setRegistrationNumber( String registrationNumber )
    {
        this.registrationNumber = registrationNumber;
    }



    public String getDetails()
    {
        return details;
    }



    public void setDetails( String details )
    {
        this.details = details;
    }



    public long getId()
    {
        return id;
    }



    public void setId( long id )
    {
        this.id = id;
    }



    public short getSector()
    {
        return sector;
    }



    public void setSector( short sector )
    {
        this.sector = sector;
    }



    public short getActivity()
    {
        return activity;
    }



    public void setActivity( short activity )
    {
        this.activity = activity;
    }
    

}
