package service.impl.assesment;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.base.Strings;

import dao.api.assessment.task.AssessmentTaskCategoryDAO;
import dao.api.assessment.task.AssessmentTaskDAO;
import model.assessment.task.AssessmentTask;
import model.assessment.task.AssessmentTaskCategory;
import model.assessment.task.AssessmentTaskDetail;
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
    public AssessmentTask createTask( String itemName, String itemContent, float itemGrade, int mode, int modeType, int complexity)
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
            task.setItemGrade( itemGrade );
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
    public AssessmentTaskDetail createTaskDetails( String itemDetail, float itemGradeRatio)
    {
        AssessmentTaskDetail taskDetails = null;
        
        try 
        {
            taskDetails = new AssessmentTaskDetail();
            taskDetails.setItemGradeRatio( itemGradeRatio );
            taskDetails.setItemDetail( itemDetail );
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
    public AssessmentTaskCategory createTaskCategory(String name, String details, int type)
    {
        AssessmentTaskCategory category = null;
        
        isValidCategoryName(name);
        
        try 
        {
            category = new AssessmentTaskCategory();
            category.setName( name );
            category.setDetails( details );
            category.setType( type );
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
    public AssessmentTask saveTask( AssessmentTask entity , long categoryId)
    {
        isValidTaskName(entity.getItemName());
        
        if(categoryId > 0)
            entity.setCategory( categoryDAO.findOne( categoryId ));
        else
            entity.setCategory( getSystemCategory());
        
        return taskDAO.save( entity );
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
    public AssessmentTask getTaskById(long taskId)
    {
        return taskDAO.findOne( taskId );
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
    public List<AssessmentTaskCategory> getCategoryShortList(String categoryName)
    {
        return categoryDAO.getShortListByCategoryName( categoryName );
    }
    
    
    /* *************************************************
     */
    @Override
    public AssessmentTaskCategory getCategoryDetails(long categoryId)
    {
        return categoryDAO.getDetailsById( categoryId );
    }
    

    /* *************************************************
     */
    @Override
    public AssessmentTaskCategory getSystemCategory()
    {
        List<AssessmentTaskCategory> elements = categoryDAO.getSystemCategory();
        return CollectionUtils.isEmpty( elements ) ? null : elements.get(0);
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
    @Override
    public AssessmentTaskCategory saveTaskCategory( AssessmentTaskCategory category, long parentCategoryId )
    {
        if(parentCategoryId != 0)
        {
            category.setParent( getCategoryDetails( parentCategoryId ) );
        }
        
        return saveTaskCategory( category );
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
