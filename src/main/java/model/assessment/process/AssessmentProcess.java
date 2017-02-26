package model.assessment.process;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import model.assessment.Assessment;
import model.assessment.task.AssessmentTask;
import model.identity.User;

@Entity
@Table( name = "asmt_process" )
public class AssessmentProcess
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @Column(name = "start_date") 
    private Date startDate = new Date(System.currentTimeMillis());
    
    @Column(name = "end_date") 
    private Date endDate = new Date(System.currentTimeMillis());
    
    @Column(name = "state") 
    private short state = 1;
    
    
    // *********************************************
    @OneToOne(  fetch = FetchType.LAZY )
    @JoinColumn(name = "user_id")
    private User user;
    // *********************************************    
    
    // *********************************************
    @ManyToOne(  fetch = FetchType.LAZY )
    @JoinColumn(name = "assessment_id")
    private Assessment assessment;
    // *********************************************  
    
    // *********************************************
    @OneToMany(mappedBy="process" , cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    private Set<ProcessResponse> responses = new HashSet<ProcessResponse>();
    // *********************************************    

    @Transient
    List<Long> taskIds;
 
    @Transient
    AssessmentTask currentTask = null;
 

    public long getId()
    {
        return id;
    }

    public long getTimeElapsed()
    {
        return endDate.getTime() - startDate.getTime() ;
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

    public User getUser()
    {
        return user;
    }

    public void setUser( User user )
    {
        this.user = user;
    }

    public Assessment getAssessment()
    {
        return assessment;
    }

    public void setAssessment( Assessment assessment )
    {
        this.assessment = assessment;
    }

    public Set<ProcessResponse> getResponses()
    {
        return responses;
    }

    public void setResponses( Set<ProcessResponse> responses )
    {
        this.responses = responses;
    }

    public int getState()
    {
        return state;
    }

    public void setState( int state )
    {
        this.state = (short)state;
    }
    
    
    public void addProcessResponse(ProcessResponse response)
    {
        this.responses.add( response );
        
        if (response.getProcess() != this) 
        {
            response.setProcess( this);
        }
    }

    public List<Long> getTaskIds()
    {
        return taskIds;
    }

    public void setTaskIds( List<Long> taskIds )
    {
        this.taskIds = taskIds;
    }

    public AssessmentTask getCurrentTask()
    {
        return currentTask;
    }

    public void setCurrentTask( AssessmentTask currentTask )
    {
        this.currentTask = currentTask;
    }
    
    

}
