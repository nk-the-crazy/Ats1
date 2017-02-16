package model.assessment.task;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "asmt_task_option" )
public class AssessmentTaskOption
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @Column(name = "item_grade")
    private float itemOptionGrade = 0;
    
    @Column(name = "item_option")
    private String itemOption;
    
    
    //***********************************
    public long getId()
    {
        return id;
    }

    

    public void setId( long id )
    {
        this.id = id;
    }



    public float getItemOptionGrade()
    {
        return itemOptionGrade;
    }


    public void setItemOptionGrade( float itemOptionGrade )
    {
        this.itemOptionGrade = itemOptionGrade;
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
