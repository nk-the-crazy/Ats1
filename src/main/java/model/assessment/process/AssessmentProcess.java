package model.assessment.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import model.assessment.Assessment;
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
    private Date startDate;
    
    @Column(name = "end_date") 
    private Date endDate;
    
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
    private List<AssessmentProcessTask> tasks = new ArrayList<AssessmentProcessTask>();
    // *********************************************    

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

    public List<AssessmentProcessTask> getTasks()
    {
        return tasks;
    }

    public void setTasks( List<AssessmentProcessTask> tasks )
    {
        this.tasks = tasks;
    }

    public int getState()
    {
        return state;
    }

    public void setState( int state )
    {
        this.state = (short)state;
    }

    public void addProcessTask(AssessmentProcessTask processTask)
    {
        this.tasks.add( processTask );
    }
    

}
