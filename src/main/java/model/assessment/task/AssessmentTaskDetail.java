package model.assessment.task;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table( name = "asmt_task_detail" )
public class AssessmentTaskDetail
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @Column(name = "item_grade_ratio")
    private float itemGradeRatio = 0;
    
    @Column(name = "item_detail")
    @Lob
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


    public float getItemGradeRatio()
    {
        return itemGradeRatio;
    }

    public void setItemGradeRatio( float itemGradeRatio )
    {
        this.itemGradeRatio = itemGradeRatio;
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
