package model.assessment.task;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table( name = "asmt_category" )
public class AssessmentTaskCategory
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "details")
    private String details;
    
    @Column(name = "type")
    private short type = 2; //Regular
    
    // *********************************************
    @OneToMany(mappedBy="category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<AssessmentTask> tasks = new HashSet<AssessmentTask>();
    // *********************************************
    
    // *********************************************
    @OneToMany(mappedBy="parent" , fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<AssessmentTaskCategory> children = new HashSet<AssessmentTaskCategory>();
    // *********************************************

    // *********************************************
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private AssessmentTaskCategory parent = null;
    // *********************************************

    
    // *********************************************
    public long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getDetails()
    {
        return details;
    }

    public void setDetails( String details )
    {
        this.details = details;
    }
    
    public Set<AssessmentTask> getTasks()
    {
        return tasks;
    }

    public void setTasks( Set<AssessmentTask> tasks )
    {
        this.tasks = tasks;
    }

    public Set<AssessmentTaskCategory> getChildren()
    {
        return children;
    }

    public void setChildren( Set<AssessmentTaskCategory> children )
    {
        this.children = children;
    }

    public AssessmentTaskCategory getParent()
    {
        return parent;
    }

    public void setParent( AssessmentTaskCategory parent )
    {
        this.parent = parent;
    }
    
    
    public void addTask(AssessmentTask task) 
    {
        tasks.add(task );
        
        this.tasks.add(task);
        
        if (task.getCategory() != this) 
        {
            task.setCategory( this );
        }

    }
    
    public void addChildCategory(AssessmentTaskCategory childCategory) 
    {
        this.children.add(childCategory);
        
        if (childCategory.getParent() != this) 
        {
            childCategory.setParent( this ); 
        }
    }

    public int getType()
    {
        return type;
    }

    public void setType( int type )
    {
        this.type = (short)type;
    }
    
    

}
