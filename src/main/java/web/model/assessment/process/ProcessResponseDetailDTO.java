package web.model.assessment.process;

public class ProcessResponseDetailDTO
{
    private long id = 0;
    private long taskDetailId = 0;
    
    private String itemResponse;
    
    public long getId()
    {
        return id;
    }
    
    public void setId( long id )
    {
        this.id = id;
    }
    
    public String getItemResponse()
    {
        return itemResponse;
    }
    
    public void setItemResponse( String itemResponse )
    {
        this.itemResponse = itemResponse;
    }

    public long getTaskDetailId()
    {
        return taskDetailId;
    }

    public void setTaskDetailId( long taskDetailId )
    {
        this.taskDetailId = taskDetailId;
    }   
    
    
}
