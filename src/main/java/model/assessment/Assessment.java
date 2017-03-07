package model.assessment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import model.assessment.options.AssessmentFormOptions;
import model.assessment.process.AssessmentProcess;
import model.assessment.task.AssessmentTask;
import model.group.UserGroup;
import model.identity.User;

@Entity
@Table( name = "assessment" )
public class Assessment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;
    
    @Column(name = "type")
    private short type = 2;

    @Column(name = "max_grade")
    private short maxGrade = 100;

    @Column(name = "time")
    private int time = 1;

    @Column(name = "status")
    private int status = 1;

    @Transient
    private long taskCount = 0;

    @Transient
    private AssessmentFormOptions formOptions;
    
    // *********************************************
    @OneToOne (fetch = FetchType.LAZY )
    @JoinColumn(name="author_id")
    private User author;
    // *********************************************

    // *********************************************
    @ManyToMany (fetch = FetchType.LAZY)
    @JoinTable( name="assessment_managers",
        joinColumns=@JoinColumn(name="assessment_id", referencedColumnName="id"),
        inverseJoinColumns=@JoinColumn(name="user_id", referencedColumnName="id"))
    private Set<User> inspectors = new HashSet<User>();
    // ********************************************* 

    // *********************************************
    @ManyToMany (fetch = FetchType.LAZY)
    @JoinTable( name="assessment_groups",
        joinColumns=@JoinColumn(name="assessment_id", referencedColumnName="id"),
        inverseJoinColumns=@JoinColumn(name="group_id", referencedColumnName="id"))
    private List<UserGroup> participants = new ArrayList<UserGroup>();
    // *********************************************

    // *********************************************
    @ManyToMany (fetch = FetchType.LAZY)
    @JoinTable( name="assessment_tasks",
        joinColumns=@JoinColumn(name="assessment_id", referencedColumnName="id"),
        inverseJoinColumns=@JoinColumn(name="task_id", referencedColumnName="id"))
    private Set<AssessmentTask> tasks = new HashSet<AssessmentTask>();
    // *********************************************

    
    // *********************************************
    @OneToMany(mappedBy="assessment" ,fetch = FetchType.LAZY )
    private Set<AssessmentProcess> processes = new HashSet<AssessmentProcess>();
    // *********************************************    

    
    public long getId()
    {
        return id;
    }
    
       
    public void setId(long id)
    {
        this.id = id;
    }
    
       
    public String getName()
    {
        return name;
    }


    public void setName( String name )
    {
        this.name = name;
    }

    
    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate( Date startDate )
    {
        this.startDate = startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate( Date endDate )
    {
        this.endDate = endDate;
    }

    public int getType()
    {
        return type;
    }

    public void setType( int type )
    {
        this.type = (short)type;
    }

  
    public List<UserGroup> getParticipants()
    {
        return participants;
    }


    public void setParticipants( List<UserGroup> participants )
    {
        this.participants = participants;
    }


    public int getStatus()
    {
        return status;
    }

    public void setStatus( int status )
    {
        this.status = status;
    }

    public Set<AssessmentTask> getTasks()
    {
        return tasks;
    }

    public void setTasks( Set<AssessmentTask> tasks )
    {
        this.tasks = tasks;
    }
    
    public User getAuthor()
    {
        return author;
    }


    public void setAuthor( User author )
    {
        this.author = author;
    }


    public Set<User> getInspectors()
    {
        return inspectors;
    }


    public void setInspectors( Set<User> inspectors )
    {
        this.inspectors = inspectors;
    }


    public int getMaxGrade()
    {
        return maxGrade;
    }


    public void setMaxGrade( int maxGrade )
    {
        this.maxGrade = (short) maxGrade;
    }


    public void addTask(AssessmentTask task) 
    {
        tasks.add(task );
    }

    public void addParticipant(UserGroup group) 
    {
        participants.add(group );
    }

    public void addInspector(User manager) 
    {
        inspectors.add(manager );
    }


    public int getTime()
    {
        return time;
    }


    public void setTime( int time )
    {
        this.time = time;
    }


    public long getTaskCount()
    {
        return taskCount;
    }


    public void setTaskCount( long taskCount )
    {
        this.taskCount = (short) taskCount;
    }


    public AssessmentFormOptions getFormOptions()
    {
        return formOptions;
    }


    public void setFormOptions( AssessmentFormOptions formOptions )
    {
        this.formOptions = formOptions;
    }


    public Set<AssessmentProcess> getProcesses()
    {
        return processes;
    }


    public void setProcesses( Set<AssessmentProcess> processes )
    {
        this.processes = processes;
    }
    
    
    public void addProcess(AssessmentProcess process)
    {
        this.processes.add( process );
        
        if (process.getAssessment() != this) 
        {
            process.setAssessment( this);
        }
    }
    
}
