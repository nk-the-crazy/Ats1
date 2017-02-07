package service.impl.assesment.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;

import dao.api.assessment.task.TaskDAO;
import model.assessment.task.AssessmentTask;
import service.api.assesment.task.AssessmentTaskManager;

@Service("taskManagerService")
@Transactional
public class AssessmentTaskManagerImpl implements AssessmentTaskManager
{
    //---------------------------------
    private static final Logger logger = LoggerFactory.getLogger(AssessmentTaskManagerImpl.class);
    //---------------------------------
            
    @Autowired
    TaskDAO taskDAO;
    
    /**************************************************
     * 
     */
    @Override
    public AssessmentTask createTask(String item, int type, String comment)
    {
        AssessmentTask task = null;
        
        isValidTaskName(item);
        
        try 
        {

        }
        catch(Exception e) 
        {
            logger.error( "Unable to create task object:", e);
        }
        
        return task;
    
    }
    
    

    /**************************************************
     * 
     */
    private void isValidTaskName( String taskName )
    {
        if ( Strings.isNullOrEmpty( taskName ) || taskName.length() < 4 )
        {
            throw new IllegalArgumentException( "Task name cannot be shorter than 4 characters." );
        }

        if ( taskName.equalsIgnoreCase( "token" ) || taskName.equalsIgnoreCase( "administrator" )
                || taskName.equalsIgnoreCase( "system" ) )
        {
            throw new IllegalArgumentException( "Task name is reserved by the system." );
        }
    }

}
