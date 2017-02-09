package service.impl.assesment;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;

import dao.api.assessment.AssessmentTaskCategoryDAO;
import dao.api.assessment.AssessmentTaskDAO;
import model.assessment.AssessmentTask;
import model.assessment.AssessmentTaskCategory;
import model.assessment.AssessmentTaskDetails;
import service.api.assessment.AssessmentTaskManager;

@Service("taskManagerService")
@Transactional
public class AssessmentTaskManagerImpl implements AssessmentTaskManager
{
    //---------------------------------
    private static final Logger logger = LoggerFactory.getLogger(AssessmentTaskManagerImpl.class);
    //---------------------------------
            
    @Autowired
    AssessmentTaskDAO taskDAO;
    
    @Autowired
    AssessmentTaskCategoryDAO categoryDAO;
    
    /**************************************************
     * 
     */
    @Override
    public AssessmentTask createTask( String itemName, String itemContent, int mode, int modeType, int complexity)
    {
        AssessmentTask task = null;
        
        isValidTaskName(itemName);
        
        try 
        {
            task = new AssessmentTask();
            task.setItemName( itemName );
            task.setComplexity( complexity );
            task.setItemContent( itemContent );
            task.setMode( mode );
            task.setModeType( modeType );
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
    @Override
    public AssessmentTaskDetails createTaskDetails( String itemOption, float grade)
    {
        AssessmentTaskDetails taskDetails = null;
        
        try 
        {
            taskDetails = new AssessmentTaskDetails();
            taskDetails.setItemGrade( grade );
            taskDetails.setItemOption( itemOption );
        }
        catch(Exception e) 
        {
            logger.error( "Unable to create task object:", e);
        }
        
        return taskDetails;
    
    }
    
    
    /**************************************************
     * 
     */
    @Override
    public AssessmentTaskCategory createTaskCategory(String name, String details)
    {
        AssessmentTaskCategory category = null;
        
        isValidCategoryName(name);
        
        try 
        {
            category = new AssessmentTaskCategory();
            category.setName( name );
            category.setDetails( details );
        }
        catch(Exception e) 
        {
            logger.error( "Unable to create task category object:", e);
        }
        
        return category;
    
    }
    
    
    /**************************************************
     * 
     */
    @Override
    public AssessmentTask saveTask( AssessmentTask entity)
    {
        isValidTaskName(entity.getItemName());
        
        return taskDAO.save( entity );
    }

    
    /**************************************************
     * 
     */
    @Override
    public AssessmentTaskCategory saveTaskCategory( AssessmentTaskCategory entity)
    {
        isValidCategoryName(entity.getName());
        
        return categoryDAO.save( entity );
    }

    /**************************************************
     * 
     */
    @Override
    public List<AssessmentTaskCategory> getCategories()
    {
        return categoryDAO.findAll();
    }
    
    
    /* *************************************************
     */
    @Override
    public List<AssessmentTaskCategory> getCategoryTree()
    {
        return categoryDAO.getCategoryTree();
    }
    

    /* *************************************************
     */
    @Override
    public AssessmentTaskCategory getCategoryDetails(long categoryId)
    {
        return categoryDAO.findOne( categoryId );
    }


    /* *************************************************
     */
    @Override
    public Page<AssessmentTask> getTasksByDetails(String itemName, String categoryName, short modeType, Pageable pageable)
    {
        return taskDAO.findByDetails( itemName, categoryName, modeType, pageable  );
    }    
    
    
    /* *************************************************
     */
    @Override
    public AssessmentTask getTaskFullDetails(long taskId)
    {
        return taskDAO.getFullDetails( taskId );
    }
    
    

    /**************************************************
     * 
     */
    @Override
    public Page<AssessmentTask> getCategoryTasks( long categoryId, Pageable pageable )
    {
        return taskDAO.getByCategoryId( categoryId, pageable);
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
    

    /**************************************************
     * 
     */
    private void isValidCategoryName( String name )
    {
        if ( Strings.isNullOrEmpty( name ) || name.length() < 4 )
        {
            throw new IllegalArgumentException( "Category name cannot be shorter than 4 characters." );
        }

        if ( name.equalsIgnoreCase( "category" ) || name.equalsIgnoreCase( "system" ) )
        {
            throw new IllegalArgumentException( "Category name is reserved by the system." );
        }
    }


}
