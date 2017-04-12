package web.model.assessment.process;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import web.model.assessment.task.TaskDTO;


public class ProcessResponseDTO
{
    private long id = 0;
    
    private TaskDTO task;
    
    private int status = 0;
    
    private int prevResponseStatus = 0;
    
    private List<ProcessResponseDetailDTO> details = new ArrayList<ProcessResponseDetailDTO>();

    public long getId()
    {
        return id;
    }

    public void setId( long id )
    {
        this.id = id;
    }

    public TaskDTO getTask()
    {
        return task;
    }

    public void setTask( TaskDTO task )
    {
        this.task = task;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus( int status )
    {
        this.status = status;
    }

    public List<ProcessResponseDetailDTO> getDetails()
    {
        return details;
    }

    public void setDetails( List<ProcessResponseDetailDTO> details )
    {
        this.details = details;
    }

    public int getPrevResponseStatus()
    {
        return prevResponseStatus;
    }

    public void setPrevResponseStatus( int prevResponseStatus )
    {
        this.prevResponseStatus = prevResponseStatus;
    }
    
    @JsonIgnore
    public void addDetail(ProcessResponseDetailDTO responseDTO)
    {
        this.details.add( responseDTO );
    }
    
}
