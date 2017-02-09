package model.assessment;

import java.util.Date;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import model.group.UserGroup;
import model.identity.User;

@Entity
@Table( name = "assesment" )
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
    private int type = 2;

    @Column(name = "status")
    private int status = 1;

    
    // *********************************************
    @OneToOne (cascade = CascadeType.ALL,fetch = FetchType.LAZY )
    @JoinColumn(name="details_id", nullable = false)
    private AssessmentDetails details;
    // *********************************************
    
    // *********************************************
    @OneToOne (cascade = CascadeType.ALL,fetch = FetchType.LAZY )
    @JoinColumn(name="author_id", nullable = false)
    private User author;
    // *********************************************

    // *********************************************
    @ManyToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable( name="asssessment_managers",
        joinColumns=@JoinColumn(name="assessment_id", referencedColumnName="id"),
        inverseJoinColumns=@JoinColumn(name="user_id", referencedColumnName="id"))
    private Set<User> managers = new HashSet<User>();
    // *********************************************

    // *********************************************
    @ManyToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable( name="asssessment_groups",
        joinColumns=@JoinColumn(name="assessment_id", referencedColumnName="id"),
        inverseJoinColumns=@JoinColumn(name="group_id", referencedColumnName="id"))
    private Set<UserGroup> groups = new HashSet<UserGroup>();
    // *********************************************

    // *********************************************
    @ManyToMany (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable( name="asssessment_tasks",
        joinColumns=@JoinColumn(name="assessment_id", referencedColumnName="id"),
        inverseJoinColumns=@JoinColumn(name="task_id", referencedColumnName="id"))
    private Set<AssessmentTask> tasks = new HashSet<AssessmentTask>();
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
        this.type = type;
    }

    public AssessmentDetails getDetails()
    {
        return details;
    }

    public void setDetails( AssessmentDetails details )
    {
        this.details = details;
    }

    public Set<UserGroup> getGroups()
    {
        return groups;
    }

    public void setGroups( Set<UserGroup> groups )
    {
        this.groups = groups;
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


    public Set<User> getManagers()
    {
        return managers;
    }


    public void setManagers( Set<User> managers )
    {
        this.managers = managers;
    }


    public void addTask(AssessmentTask task) 
    {
        tasks.add(task );
    }

    public void addGroup(UserGroup group) 
    {
        groups.add(group );
    }

    public void addManager(User manager) 
    {
        managers.add(manager );
    }


}
