package common.exceptions.dao;

public class DAOException extends Exception
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;


    public DAOException()
    {
    }


    public DAOException( Throwable cause )
    {
        super( cause );
    }


    public DAOException( String message, Throwable cause )
    {
        super( message, cause );
    }
}