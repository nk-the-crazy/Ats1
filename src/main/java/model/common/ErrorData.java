package model.common;

public class ErrorData
{
    private int errorId;
    private String errorCode;
    private Exception exception = null;
    public int getErrorId()
    {
        return errorId;
    }
    public void setErrorId( int errorId )
    {
        this.errorId = errorId;
    }
    public String getErrorCode()
    {
        return errorCode;
    }
    public void setErrorCode( String errorCode )
    {
        this.errorCode = errorCode;
    }
    public Exception getException()
    {
        return exception;
    }
    public void setException( Exception exception )
    {
        this.exception = exception;
    }
    
    
}
