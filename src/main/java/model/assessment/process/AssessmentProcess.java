package model.assessment.process;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
    
    @Column(name = "time_elapsed") //seconds
    private long timeElapsed;
    
    @Column(name = "start_date") //seconds
    private Date startDate;
    
    @Column(name = "end_date") //seconds
    private Date endDate;
    
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
    @OneToMany(  fetch = FetchType.LAZY )
    @JoinTable(name="asmt_process_tasks", 
               joinColumns=@JoinColumn(name="process_id" ),
               inverseJoinColumns=@JoinColumn(name="task_id"))
    private Set<AssessmentProcessTask> tasks = new HashSet<AssessmentProcessTask>();
    // *********************************************    

    public long getId()
    {
        return id;
    }

    public long getTimeElapsed()
    {
        return timeElapsed;
    }

    public void setTimeElapsed( long timeElapsed )
    {
        this.timeElapsed = timeElapsed;
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

    public Set<AssessmentProcessTask> getTasks()
    {
        return tasks;
    }

    public void setTasks( Set<AssessmentProcessTask> tasks )
    {
        this.tasks = tasks;
    }
  

     
}
