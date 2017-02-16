package model.assessment.task;

import java.util.ArrayList;
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table( name = "asmt_task" )
public class AssessmentTask
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @Column(name = "item_name")
    private String itemName;
    
    @Column(name = "item_content")
    @Lob
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
    
    
    // *********************************************
    @ManyToOne( fetch = FetchType.LAZY )
    @JoinColumn(name = "category_id")
    private AssessmentTaskCategory category;
    // *********************************************
     
    @OneToMany( cascade = CascadeType.ALL, fetch = FetchType.LAZY )
    @JoinTable(name="asmt_task_options", 
          joinColumns=@JoinColumn(name="task_id" , nullable = false),
          inverseJoinColumns=@JoinColumn(name="options_id"))
    private List<AssessmentTaskOption> options = new ArrayList<AssessmentTaskOption>();

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

    public AssessmentTaskCategory getCategory()
    {
        return category;
    }

    public void setCategory( AssessmentTaskCategory category )
    {
        this.category = category;
    }

    public List<AssessmentTaskOption> getOptions()
    {
        return options;
    }

    public void setOptions( List<AssessmentTaskOption> options )
    {
        this.options = options;
    }

    public void setId( long id )
    {
        this.id = id;
    }
    
    public void addDetails(AssessmentTaskOption data) 
    {
        options.add( data );
    }

    public float getItemGrade()
    {
        return itemGrade;
    }

    public void setItemGrade( float itemGrade )
    {
        this.itemGrade = itemGrade;
    }
    
}
