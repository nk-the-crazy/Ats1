package model.assessment.process;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
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

import model.assessment.task.AssessmentTask;
import model.assessment.task.AssessmentTaskResponse;


@Entity
@Table( name = "asmt_process_task" )
public class AssessmentProcessTask
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    
    // *********************************************
    @OneToOne(  fetch = FetchType.LAZY )
    @JoinColumn(name = "task_id")
    private AssessmentTask taskDetails;
    // *********************************************    
    
    // *********************************************    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="process_id")
    private AssessmentProcess process;
    // *********************************************    
    
    // *********************************************
    @OneToMany( cascade = CascadeType.ALL , fetch = FetchType.LAZY )
    @JoinTable( name="asmt_process_responses", 
                joinColumns=@JoinColumn(name="process_task_id" ),
                inverseJoinColumns=@JoinColumn(name="response_id"))
    private Set<AssessmentTaskResponse> responses = new HashSet<AssessmentTaskResponse>();
    // *********************************************    

    public long getId()
    {
        return id;
    }

    
    public AssessmentTask getTaskDetails()
    {
        return taskDetails;
    }
    

    public void setTaskDetails( AssessmentTask taskDetails )
    {
        this.taskDetails = taskDetails;
    }
    

    public Set<AssessmentTaskResponse> getResponses()
    {
        return responses;
    }

    public void setResponses( Set<AssessmentTaskResponse> responses )
    {
        this.responses = responses;
    }
    
    public void addResponse( AssessmentTaskResponse response )
    {
        this.responses.add( response );
    }

    public AssessmentProcess getProcess()
    {
        return process;
    }

    public void setProcess( AssessmentProcess process )
    {
        this.process = process;
    }
    
    
    
}
