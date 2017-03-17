package web.model.identity;

public class UserExportParams
{
    private long userId = 0;
    private int outputType = 2;
    
    private boolean contacts = false;
    private boolean address = false;
    private boolean passport = false;
    private boolean organization = false;
    private boolean education = false;
    
    public long getUserId()
    {
        return userId;
    }
    
    public void setUserId( long userId )
    {
        this.userId = userId;
    }
    
    public int getOutputType()
    {
        return outputType;
    }

    public void setOutputType( int outputType )
    {
        this.outputType = outputType;
    }

    public boolean isContacts()
    {
        return contacts;
    }
    
    public void setContacts( boolean contacts )
    {
        this.contacts = contacts;
    }
    
    public boolean isAddress()
    {
        return address;
    }
    
    public void setAddress( boolean address )
    {
        this.address = address;
    }
    
    public boolean isPassport()
    {
        return passport;
    }
    
    public void setPassport( boolean passport )
    {
        this.passport = passport;
    }
    
    public boolean isOrganization()
    {
        return organization;
    }
    
    public void setOrganization( boolean organization )
    {
        this.organization = organization;
    }
    
    public boolean isEducation()
    {
        return education;
    }
    
    public void setEducation( boolean education )
    {
        this.education = education;
    }
    
    
    
    
    
}
