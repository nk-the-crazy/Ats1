package model.assessment.process;

import java.util.Date;

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
import model.identity.User;


@Entity
@Table( name = "asmt_process_response_rate" )
public class ProcessResponseRate
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "date")
    private Date date = new Date(System.currentTimeMillis());

    @Column(name = "grade")
    private float grade = 0;
    
    @Column(name = "comment")
    @Lob
    private String comment;    
    
    // *********************************************
    @OneToOne(  fetch = FetchType.LAZY )
    @JoinColumn(name = "user_id")
    private User user;
    // *********************************************  
    
    // *********************************************
    @OneToOne(  fetch = FetchType.LAZY )
    @JoinColumn(name = "response_detail_id")
    private ProcessResponse response;
    // *********************************************  
    
    
    public long getId()
    {
        return id;
    }
    
    public void setId( long id )
    {
        this.id = id;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment( String comment )
    {
        this.comment = comment;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser( User user )
    {
        this.user = user;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate( Date date )
    {
        this.date = date;
    }

    public float getGrade()
    {
        return grade;
    }

    public void setGrade( float grade )
    {
        this.grade = grade;
    }

    public ProcessResponse getResponse()
    {
        return response;
    }

    public void setResponse( ProcessResponse response )
    {
        this.response = response;
    }


}
