package web.model.assessment.task;

import java.util.ArrayList;
import java.util.List;

public class TaskCategoryDTO
{
    private long id = 0;
    private String name  = "";
    private String description  = "";
    private List<TaskCategoryDTO> children = new ArrayList<TaskCategoryDTO>();
    
    public long getId()
    {
        return id;
    }
    
    public void setId( long id )
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
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription( String description )
    {
        this.description = description;
    }
    
    public List<TaskCategoryDTO> getChildren()
    {
        return children;
    }
    
    public void setChildren( List<TaskCategoryDTO> children )
    {
        this.children = children;
    }
    
    public void addChild(TaskCategoryDTO child)
    {
        children.add( child );
    }
}
