package web.model.assessment.process;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import common.utils.system.SystemUtils;
import model.assessment.process.ProcessResponse;
import model.assessment.process.ProcessResponseDetail;
import model.assessment.task.AssessmentTask;
import model.assessment.task.AssessmentTaskDetail;

public class ProcessResponseDataDTO
{
    private static int index = 0;
    
    @JsonIgnore
    private AssessmentTask task;
    
    @JsonIgnore
    private ProcessResponse response;
    
    @JsonIgnore
    private ProcessResponseDetail responseDetail;
    
    @JsonIgnore
    private AssessmentTaskDetail taskDetail;
    
    public ProcessResponseDataDTO(AssessmentTask task, 
                              ProcessResponse response, 
                              ProcessResponseDetail responseDetail,
                              AssessmentTaskDetail obj)
    {
        this.task = task;
        this.response = response;
        this.responseDetail = responseDetail;
        this.taskDetail = obj;
        
        index ++;
    }
    
   
    @JsonProperty("id")
    public long getId()
    {
        if(response != null)
            return response.getId();
        else
            return 0;
    }
    
    @JsonProperty("grade")
    public float getGrade()
    {
        if(response != null)
            return response.getGrade();
        else
            return 0;
    }
    
    @JsonProperty("responseDetailId")
    public long getResponseDetailId()
    {
        if(responseDetail != null)
            return responseDetail.getId();
        else
            return 0;
    }
    
    @JsonProperty("itemResponse")
    public String getItemResponse()
    {
        if(responseDetail != null)
            return responseDetail.getItemResponse();
        else
            return "";
    }
    
    @JsonProperty("itemDetail")
    public String getItemDetail()
    {
        if(taskDetail != null)
            return taskDetail.getItemDetail();
        else
            return "";
    }
    
    @JsonProperty("taskId")
    public long getTaskId()
    {
        if(task != null)
            return task.getId();
        else
            return 0;
    }
    
    @JsonProperty("taskItemContent")
    public String getTaskItemContent()
    {
        if(task != null)
            return task.getItemContent();
        else
            return "";
    }
    
    @JsonProperty("taskModeType")
    public int getTaskModeType()
    {
        if(task != null)
            return task.getModeType();
        else
            return 1;
    }
    
    @JsonProperty("taskModeTypeName")
    public String getTaskModeTypeName()
    {
        return SystemUtils.getAttribute( "system.attrib.task.mode.type", getTaskModeType() );
    }

    public int getIndex()
    {
        return index;
    }
    
}
