package model.assessment.process;

import java.util.ArrayList;
import java.util.List;

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


@Entity
@Table( name = "asmt_process_response" )
public class ProcessResponse
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    
    // *********************************************
    @OneToOne(  fetch = FetchType.LAZY )
    @JoinColumn(name = "task_id")
    private AssessmentTask task;
    // *********************************************    
    
    // *********************************************    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="process_id")
    private Process process;
    // *********************************************    
    
    // *********************************************
    @OneToMany( cascade = CascadeType.ALL , fetch = FetchType.LAZY )
    @JoinTable( name="asmt_process_responses", 
                joinColumns=@JoinColumn(name="response_id" ),
                inverseJoinColumns=@JoinColumn(name="details_id"))
    private List<ProcessResponseDetail> details = new ArrayList<ProcessResponseDetail>();
    // *********************************************    

    public long getId()
    {
        return id;
    }

   
    public AssessmentTask getTask()
    {
        return task;
    }


    public void setTask( AssessmentTask task )
    {
        this.task = task;
    }


    public List<ProcessResponseDetail> getDetails()
    {
        return details;
    }


    public void setDetails( List<ProcessResponseDetail> details )
    {
        this.details = details;
    }

    public void addResponseDetail( ProcessResponseDetail detail )
    {
        this.details.add( detail );
    }
 
    public Process getProcess()
    {
        return process;
    }

    public void setProcess( Process process )
    {
        this.process = process;
        
        if (!process.getResponses().contains(this)) 
        { 
            process.getResponses().add(this);
        }
    }

}
