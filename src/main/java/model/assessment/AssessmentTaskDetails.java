package model.assessment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "asmt_task_details" )
public class AssessmentTaskDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @Column(name = "grade")
    private float grade = 0;
    
    @Column(name = "item_details")
    private String itemDetails;
    
    
    //***********************************
    public long getId()
    {
        return id;
    }

    public float getGrade()
    {
        return grade;
    }

    public void setGrade( float grade )
    {
        this.grade = grade;
    }

    public String getItemDetails()
    {
        return itemDetails;
    }

    public void setItemDetails( String itemDetails )
    {
        this.itemDetails = itemDetails;
    }
    
}
