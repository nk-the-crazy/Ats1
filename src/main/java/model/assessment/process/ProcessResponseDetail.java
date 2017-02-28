package model.assessment.process;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import model.assessment.task.AssessmentTaskDetail;


@Entity
@Table( name = "asmt_process_response_detail" )
public class ProcessResponseDetail
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "grade")
    float grade = 0;
    
    @Column(name = "response")
    @Lob
    private String itemResponse;    
    
    // *********************************************
    @OneToOne( fetch = FetchType.LAZY  )
    @JoinColumn(name = "task_detail_id")
    private AssessmentTaskDetail taskDetail;
    // *********************************************  

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

    public AssessmentTaskDetail getTaskDetail()
    {
        return taskDetail;
    }

    public void setTaskDetail( AssessmentTaskDetail taskDetail )
    {
        this.taskDetail = taskDetail;
    }

    public float getGrade()
    {
        return grade;
    }

    public void setGrade( float grade )
    {
        this.grade = grade;
    }

   
}
