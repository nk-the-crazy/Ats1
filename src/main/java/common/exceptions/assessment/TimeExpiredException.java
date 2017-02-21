package common.exceptions.assessment;


public class TimeExpiredException  extends Exception
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public TimeExpiredException()
    {
        super();
    }


    public TimeExpiredException( final String message )
    {
        super( message );
    }


    public TimeExpiredException( final String message, final Throwable cause )
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
