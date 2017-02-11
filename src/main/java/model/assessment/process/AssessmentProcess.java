package model.assessment.process;

public class AssessmentProcess
{
    private long id = 0;
    private long userId = 0;
    private short taskCount = 0;
    private String name = "";
    private Object object = null;
    private short time = 0;
    private short state = 1; // Ready State
    
    
    public long getId()
    {
        return id;
    }
    
    public void setId( long id )
    {
        this.id = id;
    }
    public short getTaskCount()
    {
        return taskCount;
    }
    
    public void setTaskCount( int taskCount )
    {
        this.taskCount = (short)taskCount;
    }
    
    public String getName()
    {
        return name;
    }
    public void setName( String name )
    {
        this.name = name;
    }
   
    public long getUserId()
    {
        return userId;
    }
    
    public void setUserId( long userId )
    {
        this.userId = userId;
    }

    public Object getObject()
    {
        return object;
    }

    public void setObject( Object object )
    {
        this.object = object;
    }

    public short getTime()
    {
        return time;
    }

    public void setTime( int time )
    {
        this.time = (short)time;
    }

    public int getState()
    {
        return state;
    }

    public void setState( int state )
    {
        this.state = (short)state;
    }

   
  
}
