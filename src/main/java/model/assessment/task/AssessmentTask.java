package model.assessment.task;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "assessment_task" )
public class AssessmentTask
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @Column(name = "item")
    private String item;
    
    @Column(name = "comment")
    private String comment;

    @Column(name = "type")
    private int type;

    @Column(name = "status")
    private int status;

    public long getId()
    {
        return id;
    }

    public int getType()
    {
        return type;
    }

    public void setType( int type )
    {
        this.type = type;
    }

  
    
    
}
