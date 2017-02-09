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
    
    @Column(name = "item_grade")
    private float itemGrade = 0;
    
    @Column(name = "item_option")
    private String itemOption;
    
    
    //***********************************
    public long getId()
    {
        return id;
    }


    public float getItemGrade()
    {
        return itemGrade;
    }


    public void setItemGrade( float itemGrade )
    {
        this.itemGrade = itemGrade;
    }


    public String getItemOption()
    {
        return itemOption;
    }


    public void setItemOption( String itemOption )
    {
        this.itemOption = itemOption;
    }
  
}
