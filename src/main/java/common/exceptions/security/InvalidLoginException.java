package common.exceptions.security;

public class InvalidLoginException extends SystemSecurityException
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public InvalidLoginException()
    {
        super();
    }


    public InvalidLoginException( final String message )
    {
        super( message );
    }


    public InvalidLoginException( final String message, final Throwable cause )
    {
        super( message, cause );
    }
    

    @Override
    public String toString()
    {
        return super.toString();
    }

    @Override
    public StackTraceElement[] getStackTrace()
    {
        return super.getStackTrace();
    }
        

}
