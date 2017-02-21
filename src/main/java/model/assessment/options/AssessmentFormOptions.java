package model.assessment.options;

import java.util.ArrayList;
import java.util.List;

public class AssessmentFormOptions
{
    private boolean allCategories = false;
    
    private List<TaskFormOptions> taskOptions = new ArrayList<TaskFormOptions>();
    private List<Long> taskCategories = new ArrayList<Long>();

   
      public List<TaskFormOptions> getTaskOptions()
    {
        return taskOptions;
    }

    public void setTaskOptions( List<TaskFormOptions> taskOptions )
    {
        this.taskOptions = taskOptions;
    }

    public boolean isAllCategories()
    {
        return allCategories;
    }

    public void setAllCategories( boolean allCategories )
    {
        this.allCategories = allCategories;
    }

    public List<Long> getTaskCategories()
    {
        return taskCategories;
    }

    public void setTaskCategories( List<Long> taskCategories )
    {
        this.taskCategories = taskCategories;
    }

    
}


