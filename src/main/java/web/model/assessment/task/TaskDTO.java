package web.model.assessment.task;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import common.utils.system.SystemUtils;

public class TaskDTO
{
    private long id = 0;
    private String itemName = "";
    private String itemContent = "";
    private int modeType = 1;
    
    private List<TaskDetailDTO> details = new ArrayList<TaskDetailDTO>();
    
    public long getId()
    {
        return id;
    }
    
    public void setId( long id )
    {
        this.id = id;
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
    
    
    public int getModeType()
    {
        return modeType;
    }

    public void setModeType( int modeType )
    {
        this.modeType = modeType;
    }

    @JsonProperty("modeTypeName")
    public String getModeTypeName()
    {
        return SystemUtils.getAttribute( "system.attrib.task.mode.type", getModeType() );
    }

    public List<TaskDetailDTO> getDetails()
    {
        return details;
    }

    public void setDetails( List<TaskDetailDTO> details )
    {
        this.details = details;
    }

    @JsonIgnore
    public void addTaskDetail(TaskDetailDTO taskDetail)
    {
        this.details.add( taskDetail );
    }
        
}
