package common.exceptions.security;

public class InvalidPasswordException extends SystemSecurityException
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public InvalidPasswordException()
    {
        super();
    }


    public InvalidPasswordException( final String message )
    {
        super( message );
    }


    public InvalidPasswordException( final String message, final Throwable cause )
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
