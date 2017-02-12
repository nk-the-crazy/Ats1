package model.assessment.task;

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


@Entity
@Table( name = "asmt_process_response" )
public class AssessmentTaskResponse
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "short_response")
    private String shortResponse;    
    
    @Column(name = "response")
    @Lob
    private String response;    
    
    // *********************************************
    @OneToOne(  fetch = FetchType.LAZY )
    @JoinColumn(name = "user_id")
    private AssessmentTaskOption option;
    // *********************************************  

    public long getId()
    {
        return id;
    }

    public String getShortResponse()
    {
        return shortResponse;
    }

    public void setShortResponse( String shortResponse )
    {
        this.shortResponse = shortResponse;
    }

    public String getResponse()
    {
        return response;
    }

    public void setResponse( String response )
    {
        this.response = response;
    }

    public AssessmentTaskOption getOption()
    {
        return option;
    }

    public void setOption( AssessmentTaskOption option )
    {
        this.option = option;
    }


}
