package model.assessment.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProcessSession
{
    private long processId = 0;
    private long assessmentId = 0;
    private String assessmentName = "";
    private int  taskIndex = 0;
    private int  assessmentTime = 0;
    private ProcessResponse processResponse = null;
    
    private Date startDate;
    private Date endDate;
    
    
    List<Long> taskIds = new ArrayList<Long>();
    List<Integer> taskStatus = new ArrayList<Integer>();
    
    public long getProcessId()
    {
        return processId;
    }
    
    public void setProcessId( long processId )
    {
        this.processId = processId;
    }
    
    public long getAssessmentId()
    {
        return assessmentId;
    }
    
    public void setAssessmentId( long assessmentId )
    {
        this.assessmentId = assessmentId;
    }
    
    public String getAssessmentName()
    {
        return assessmentName;
    }
    
    public void setAssessmentName( String assessmentName )
    {
        this.assessmentName = assessmentName;
    }
    
    public int getTaskIndex()
    {
        return taskIndex;
    }
    
    public void setTaskIndex( int taskIndex )
    {
        this.taskIndex = taskIndex;
    }
    
    public ProcessResponse getProcessResponse()
    {
        return processResponse;
    }

    public void setProcessResponse( ProcessResponse processResponse )
    {
        this.processResponse = processResponse;
    }

    public List<Long> getTaskIds()
    {
        return taskIds;
    }
    
    public void setTaskIds( List<Long> taskIds )
    {
        this.taskIds = taskIds;
    }
    
    public List<Integer> getTaskStatus()
    {
        return taskStatus;
    }
    
    public void setTaskStatus( List<Integer> taskStatus )
    {
        this.taskStatus = taskStatus;
    }
    
    public int getAssessmentTime()
    {
        return assessmentTime;
    }

    public void setAssessmentTime( int assessmentTime )
    {
        this.assessmentTime = assessmentTime;
    }
    
    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate( Date startDate )
    {
        this.startDate = startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate( Date endDate )
    {
        this.endDate = endDate;
    }
    
    public long getTimeElapsed()
    {
        return endDate.getTime() - startDate.getTime() ;
    }
    
    public void addTaskDetails(long taskId , int status)
    {
        taskIds.add( taskId );
        taskStatus.add( status );
    }
    
    public long getTaskIdByIndex(int index)
    {
        if(index >= taskIds.size())
            return 0;
        else
            return taskIds.get( index );
    }
    
}
