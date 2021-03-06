package model.assessment.task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table( name = "asmt_task" )
public class AssessmentTask implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 7805653327391514635L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @Column(name = "item_name")
    private String itemName;
    
    @Column(name = "item_content", columnDefinition = "text")
    private String itemContent;
    
    @Column(name = "item_grade")
    private float itemGrade = 0;
    
    @Column(name = "mode_type")
    private short modeType = 1;

    @Column(name = "status")
    private short status = 1;
    
    @Column(name = "mode")
    private short mode = 2;
    
    @Column(name = "complexity")
    private short complexity = 2;
    
    @Column(name = "detail_ordering")
    private short detailOrdering = 2; // Orderable
    
   // *********************************************
    @OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY )
    @JoinColumn(name="info_id")
    private AssessmentTaskInfo detailInfo;
    
    
    // *********************************************
    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn(name = "category_id")
    private AssessmentTaskCategory category;
    // *********************************************
     
    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    @JoinTable(name="asmt_task_details", 
          joinColumns=@JoinColumn(name="task_id" , nullable = false),
          inverseJoinColumns=@JoinColumn(name="details_id"))
    private List<AssessmentTaskDetail> details = new ArrayList<AssessmentTaskDetail>();

    // *********************************************
    public long getId()
    {
        return id;
    }

    public String getItemName()
    {
        return itemName;
    }

    public void setItemName( String itemName )
    {
        this.itemName = itemName;
    }

  
    public String getItemContent()
    {
        return itemContent;
    }

    public void setItemContent( String itemContent )
    {
        this.itemContent = itemContent;
    }

    public short getModeType()
    {
        return modeType;
    }

    public void setModeType( int modeType )
    {
        this.modeType = (short) modeType;
    }

    public short getStatus()
    {
        return status;
    }

    public void setStatus( int status )
    {
        this.status = (short) status;
    }

    public short getMode()
    {
        return mode;
    }

    public void setMode( int mode )
    {
        this.mode = (short) mode;
    }

    public short getComplexity()
    {
        return complexity;
    }

    public void setComplexity( int complexity )
    {
        this.complexity = (short) complexity;
    }
    
    public int getDetailOrdering()
    {
        return detailOrdering;
    }

    public void setDetailOrdering( int detailOrdering )
    {
        this.detailOrdering = (short) detailOrdering;
    }

    public AssessmentTaskCategory getCategory()
    {
        return category;
    }

    public void setCategory( AssessmentTaskCategory category )
    {
        this.category = category;
    }

    public List<AssessmentTaskDetail> getDetailsRandom()
    {
        if(modeType == 3 || modeType == 4 )
            return details;
        
        if(detailOrdering != AssessmentTaskOrdering.Static.getId())
        {
            Collections.shuffle( details );
        }
        
        return details;
    }
    
    public List<AssessmentTaskDetail> getDetails()
    {
        return details;
    }

    public void setDetails( List<AssessmentTaskDetail> details )
    {
        this.details = details;
    }

    public void setId( long id )
    {
        this.id = id;
    }
    
    public void addDetail(AssessmentTaskDetail detail) 
    {
        details.add( detail );
    }

    public float getItemGrade()
    {
        return itemGrade;
    }

    public void setItemGrade( float itemGrade )
    {
        this.itemGrade = itemGrade;
    }

    public AssessmentTaskInfo getDetailInfo()
    {
        return detailInfo;
    }

    public void setDetailInfo( AssessmentTaskInfo detailInfo )
    {
        this.detailInfo = detailInfo;
    }

    
}
