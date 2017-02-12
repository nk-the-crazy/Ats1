package model.assessment.process;

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
    private AssessmentTask task;
    // *********************************************    
    
    // *********************************************
    @OneToMany(  fetch = FetchType.LAZY )
    @JoinTable(name="asmt_process_responses", 
                joinColumns=@JoinColumn(name="process_task_id" ),
                inverseJoinColumns=@JoinColumn(name="response_id"))
    private Set<AssessmentTaskResponse> responses = new HashSet<AssessmentTaskResponse>();
    // *********************************************    
    
    
    
}
