package service.impl.assesment;


import java.util.List;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Strings;

import common.utils.StringUtils;
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
    // ---------------------------------
    private static final Logger logger = LoggerFactory.getLogger( AssessmentTaskManagerImpl.class );
    // ---------------------------------

    @Autowired
    AssessmentTaskDAO taskDAO;

    @Autowired
    AssessmentTaskCategoryDAO categoryDAO;

    /**************************************************
     * 
     */
    @Override
    public AssessmentTask createTask( String itemName, String itemContent, float itemGrade, int mode, int modeType,
                    int complexity )
    {
        AssessmentTask task = null;

        isValidTaskName( itemName );

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
        catch ( Exception e )
        {
            logger.error( "Unable to create task object:", e );
        }

        return task;

    }

    /**************************************************
     * 
     */
    @Override
    public AssessmentTaskDetail createTaskDetails( String itemDetail, float itemGradeRatio )
    {
        AssessmentTaskDetail taskDetails = null;

        try
        {
            taskDetails = new AssessmentTaskDetail();
            taskDetails.setItemGradeRatio( itemGradeRatio );
            taskDetails.setItemDetail( itemDetail );
        }
        catch ( Exception e )
        {
            logger.error( "Unable to create task object:", e );
        }

        return taskDetails;

    }

    /**************************************************
     * 
     */
    @Override
    public AssessmentTaskCategory createTaskCategory( String name, String details, int type )
    {
        AssessmentTaskCategory category = null;

        isValidCategoryName( name );

        try
        {
            category = new AssessmentTaskCategory();
            category.setName( name );
            category.setDetails( details );
            category.setType( type );
        }
        catch ( Exception e )
        {
            logger.error( "Unable to create task category object:", e );
        }

        return category;

    }

    /**************************************************
     * 
     */
    @Override
    public AssessmentTask saveTask( AssessmentTask entity, long categoryId )
    {
        isValidTaskName( entity.getItemName() );

        if ( categoryId > 0 )
            entity.setCategory( categoryDAO.findOne( categoryId ) );
        else
            entity.setCategory( getSystemCategory() );

        return taskDAO.save( entity );
    }

    /**************************************************
     * 
     */
    @Override
    public AssessmentTask saveTask( AssessmentTask entity )
    {
        isValidTaskName( entity.getItemName() );

        return taskDAO.save( entity );
    }

    /**************************************************
     * 
     */
    @Override
    public AssessmentTaskCategory saveTaskCategory( AssessmentTaskCategory entity )
    {
        isValidCategoryName( entity.getName() );

        return categoryDAO.save( entity );
    }

    /**************************************************
     * 
     */
    @Override
    public AssessmentTask getTaskById( long taskId )
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

    /*
     * *************************************************
     */
    @Override
    public List<AssessmentTaskCategory> getCategoryTree()
    {
        return categoryDAO.getCategoryTree();
    }

    /*
     * *************************************************
     */
    @Override
    public List<AssessmentTaskCategory> getCategoryShortList( String categoryName )
    {
        return categoryDAO.getShortListByCategoryName( categoryName );
    }

    /*
     * *************************************************
     */
    @Override
    public AssessmentTaskCategory getCategoryDetails( long categoryId )
    {
        return categoryDAO.getDetailsById( categoryId );
    }

    /*
     * *************************************************
     */
    @Override
    public AssessmentTaskCategory getSystemCategory()
    {
        List<AssessmentTaskCategory> elements = categoryDAO.getSystemCategory();
        return CollectionUtils.isEmpty( elements ) ? null : elements.get( 0 );
    }

    /*
     * *************************************************
     */
    @Override
    public Page<AssessmentTask> getTasksByDetails( String itemName, String categoryName, short modeType,
                    Pageable pageable )
    {
        return taskDAO.findByDetails( itemName, categoryName, modeType, pageable );
    }

    /*
     * *************************************************
     */
    @Override
    public AssessmentTask getTaskFullDetails( long taskId )
    {
        return taskDAO.getFullDetails( taskId );
    }

    /**************************************************
     * 
     */
    @Override
    public Page<AssessmentTask> getCategoryTasks( long categoryId, Pageable pageable )
    {
        return taskDAO.getByCategoryId( categoryId, pageable );
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
        if ( parentCategoryId != 0 )
        {
            category.setParent( getCategoryDetails( parentCategoryId ) );
        }

        return saveTaskCategory( category );
    }
    
    
    /**************************************************
     * 
     */
    @Override
    public void importTasks( MultipartFile file )
    {
        try
        {
            String lowerCaseFileName = file.getOriginalFilename().toLowerCase();

            if ( lowerCaseFileName.contains( ".xls" ) )
            {
                Workbook offices = null;

                logger.info( "Excel file uploaded:" + lowerCaseFileName );

                try
                {
                    if ( lowerCaseFileName.endsWith( ".xlsx" ) )
                    {
                        offices = new XSSFWorkbook( file.getInputStream() );
                    }
                    else
                    {
                        offices = new HSSFWorkbook( file.getInputStream() );
                    }

                    Sheet worksheet = offices.getSheetAt( 0 );
                    Iterator<Row> iterator = worksheet.iterator();
                    AssessmentTaskCategory parentCategory = null;
                    AssessmentTaskCategory category = null;
                    AssessmentTask task = null;
                    AssessmentTaskDetail detail = null;
                    int index = 0;
                    int taskCount = 0;

                    while ( iterator.hasNext() )
                    {
                        index++;
                        Row currentRow = iterator.next();
                        Cell currentCell = currentRow.getCell( 4 );

                        if ( currentCell != null && currentCell.getCellType() != Cell.CELL_TYPE_BLANK )
                        {
                            int recordType = 0;

                            if ( currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC )
                                recordType = (int) currentCell.getNumericCellValue();
                            else if ( currentCell.getCellType() == Cell.CELL_TYPE_STRING )
                                recordType = Integer.parseInt( currentCell.getStringCellValue() );

                            logger.info( "Excel row:" + index + " column[4]:" + recordType );

                            if ( recordType == 1 ) // Parent Category
                            {
                                currentCell = currentRow.getCell( 0 );
                                parentCategory = createTaskCategory( currentCell.getStringCellValue().trim(), "", 2 );
                                categoryDAO.save( parentCategory );
                                category = null;

                                logger.info( "Excel Category inserted:" + parentCategory.getName() );
                            }
                            else if ( recordType == 2 ) // Category
                            {
                                currentCell = currentRow.getCell( 0 );
                                category = createTaskCategory( currentCell.getStringCellValue().trim(), "", 2 );
                                category.setParent( parentCategory );
                                categoryDAO.save( category );
                                task = null;

                                logger.info( "Excel Sub Category inserted:" + category.getName() );
                            }
                            else if ( recordType == 3 ) // Task
                            {
                                int modeType = 0;
                                int complexity = 0;

                                currentCell = currentRow.getCell( 0 );
                                String itemContent = currentCell.getStringCellValue();
                                itemContent = StringUtils.formatSpecial( itemContent );

                                currentCell = currentRow.getCell( 5 );

                                if ( currentCell.getCellType() == Cell.CELL_TYPE_FORMULA )
                                {
                                    if ( currentCell.getCachedFormulaResultType() == Cell.CELL_TYPE_NUMERIC )
                                        complexity = (int) currentCell.getNumericCellValue();
                                    else if ( currentCell.getCachedFormulaResultType() == Cell.CELL_TYPE_STRING )
                                        complexity = Integer.parseInt( currentCell.getStringCellValue() );
                                }

                                currentCell = currentRow.getCell( 6 );

                                if ( currentCell.getCellType() == Cell.CELL_TYPE_FORMULA )
                                {
                                    if ( currentCell.getCachedFormulaResultType() == Cell.CELL_TYPE_NUMERIC )
                                        modeType = (int) currentCell.getNumericCellValue();
                                    else if ( currentCell.getCachedFormulaResultType() == Cell.CELL_TYPE_STRING )
                                        modeType = Integer.parseInt( currentCell.getStringCellValue() );
                                }

                                taskCount++;
                                task = createTask( String.format( "Вопрос-%04d", taskCount ), itemContent, 1, 1,
                                                modeType, complexity );
                                task.setCategory( category );
                                detail = null;

                                logger.info( "Excel Task inserted:" + task.getItemContent() );
                            }
                            else if ( recordType == 4 ) // Task Details
                            {
                                if ( task != null )
                                {
                                    float itemGradeRatio = 0;

                                    currentCell = currentRow.getCell( 1 );

                                    if ( currentCell != null && currentCell.getCellType() != Cell.CELL_TYPE_BLANK )
                                    {
                                        String value = "";

                                        if ( currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC )
                                            value = String.valueOf( currentCell.getNumericCellValue() );
                                        else if ( currentCell.getCellType() == Cell.CELL_TYPE_STRING )
                                            value = currentCell.getStringCellValue();

                                        if ( Strings.isNullOrEmpty( value.trim() ) )
                                            itemGradeRatio = 0;
                                        else
                                            itemGradeRatio = 100;
                                    }
                                    else
                                    {
                                        itemGradeRatio = 0;
                                    }

                                    currentCell = currentRow.getCell( 0 );
                                    String itemDetail = currentCell.getStringCellValue();
                                    itemDetail = StringUtils.formatSpecial( itemDetail );

                                    detail = createTaskDetails( itemDetail, itemGradeRatio );

                                    task.addDetail( detail );
                                    taskDAO.save( task );
                                }
                            }
                        }

                    }
                }
                catch ( Exception e )
                {
                    throw e;
                }
                finally
                {
                    offices.close();
                }

            }
        }
        catch ( Exception e )
        {
            logger.error( "******** Error import data from file:", e );
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
