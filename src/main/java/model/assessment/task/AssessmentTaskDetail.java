package model.assessment.task;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "asmt_task_detail" )
public class AssessmentTaskDetail
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @Column(name = "item_grade")
    private float itemGrade = 0;
    
    @Column(name = "item_detail")
    private String itemDetail;
    
    
    //***********************************
    public long getId()
    {
        return id;
    }
    

    public void setId( long id )
    {
        this.id = id;
    }


    public float getItemGrade()
    {
        return itemGrade;
    }


    public void setItemGrade( float itemGrade )
    {
        this.itemGrade = itemGrade;
    }

    public String getItemDetail()
    {
        return itemDetail;
    }


    public void setItemDetail( String itemDetail )
    {
        this.itemDetail = itemDetail;
    }

}
