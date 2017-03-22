package web.model.assessment.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import common.utils.system.SystemUtils;

public class TaskDTO
{
    private long id = 0;
    private String itemName = "";
    private String itemContent = "";
    private int modeType = 1;
    
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
        
}
