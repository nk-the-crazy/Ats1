package model.person;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import model.person.PersonDetails;

@Entity
@Table(name = "person_details")
public class PersonDetails
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column( name = "id")
    private long id;
    
    @Column( name = "gender" )
    private short gender = 1;
    
    @Column( name = "activity" )
    private String activity;
    
    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "tax_payer_number") // Tax payer NUmber
    private String taxPayerNumber = "";

    @Column(name = "passport_serial") // Passport Serial
    private String passportSerial = "";

    @Column(name = "passport_number") // Passport NUmber
    private String passportNumber = "";
    
    @Column(name = "passport_valid_date") // Passport NUmber
    private Date passportValidDate;
    
    @Column(name = "passport_issued_date") // Passport NUmber
    private Date passportIssuedDate;

    @Column(name = "passport_issued_by") // Passport NUmber
    private String passportIssuedBy;

    @Column(name = "details") // Passport NUmber
    private String details = "";
    
   

    /*********************************************************
     * 
     */

    public Date getBirthDate()
    {
        return birthDate;
    }
    
    
    public String getBirthDateAsString()
    {
        return "";// SystemSettings.DATE_FORMAT.format( birthDate );
    }


    
    public void setBirthDate( Date birthDate )
    {
        this.birthDate = birthDate;
    }
    
    public String getTaxPayerNumber()
    {
        return taxPayerNumber;
    }
    
    public void setTaxPayerNumber( String taxPayerNumber )
    {
        this.taxPayerNumber = taxPayerNumber;
    }
    
    public String getPassportSerial()
    {
        return passportSerial;
    }
    
    public void setPassportSerial( String passportSerial )
    {
        this.passportSerial = passportSerial;
    }
    
    public String getPassportNumber()
    {
        return passportNumber;
    }


    
    public void setPassportNumber( String passportNumber )
    {
        this.passportNumber = passportNumber;
    }


    public long getId()
    {
        return id;
    }


    public String getDetails()
    {
        return details;
    }


    public void setDetails( String details )
    {
        this.details = details;
    }


    public int getGender()
    {
        return gender;
    }


    public void setGender( int gender )
    {
        this.gender = (short)gender;
    }


    public Date getPassportValidDate()
    {
        return passportValidDate;
    }


    public void setPassportValidDate( Date passportValidDate )
    {
        this.passportValidDate = passportValidDate;
    }


    public Date getPassportIssuedDate()
    {
        return passportIssuedDate;
    }


    public void setPassportIssuedDate( Date passportIssuedDate )
    {
        this.passportIssuedDate = passportIssuedDate;
    }


    public String getPassportIssuedBy()
    {
        return passportIssuedBy;
    }


    public void setPassportIssuedBy( String passportIssuedBy )
    {
        this.passportIssuedBy = passportIssuedBy;
    }


    public String getActivity()
    {
        return activity;
    }


    public void setActivity( String activity )
    {
        this.activity = activity;
    }


}
