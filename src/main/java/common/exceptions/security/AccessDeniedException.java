package common.exceptions.security;

public class AccessDeniedException extends SystemSecurityException
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public AccessDeniedException()
    {
        super();
    }


    public AccessDeniedException( final String message )
    {
        super( message );
    }


    public AccessDeniedException( final String message, final Throwable cause )
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
