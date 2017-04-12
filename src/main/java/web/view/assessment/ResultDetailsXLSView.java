package web.view.assessment;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import common.utils.StringUtils;
import common.utils.system.SystemUtils;
import model.assessment.process.ProcessResponse;
import model.assessment.task.AssessmentTask;
import model.assessment.task.AssessmentTaskDetail;
import model.identity.User;
import model.report.assessment.AssessmentResult;


public class ResultDetailsXLSView extends AbstractXlsxView  implements MessageSourceAware
{
    //---------------------------------
    private static final Logger logger = LoggerFactory.getLogger(ResultDetailsXLSView.class);
    //---------------------------------
    
    private MessageSource messageSource;
    
    @Override
    protected void buildExcelDocument( Map<String, Object> model, Workbook workbook, HttpServletRequest request,
                    HttpServletResponse response ) throws Exception
    {

        Locale locale = LocaleContextHolder.getLocale();
        
        try
        {
            int rowCount = 1;
            int column = 0;
            Cell cell = null;
            long taskId = 0;
            
            @SuppressWarnings("unchecked")
            Page<Object[]> resultsPage = (Page<Object[]>) model.get( "responsesPage" );
            AssessmentResult result = (AssessmentResult) model.get( "assessmentResult" );
            User userDetails = (User) model.get( "userDetails" );

            response.setHeader( "Content-Disposition", "attachment; filename=\"results.xlsx\"" );

            // create excel xls sheet
            Sheet sheet = workbook.createSheet( messageSource.getMessage( "label.page.report_result_list.title", null, locale) );
            
            CellStyle styleAnswer = workbook.createCellStyle();
            styleAnswer.setFillForegroundColor( IndexedColors.ROSE.index);
            styleAnswer.setFillPattern(CellStyle.SOLID_FOREGROUND);
            styleAnswer.setBorderBottom( CellStyle.BORDER_THIN );
            styleAnswer.setBorderTop( CellStyle.BORDER_THIN);
            styleAnswer.setBorderRight( CellStyle.BORDER_THIN );
            styleAnswer.setBorderLeft( CellStyle.BORDER_THIN );

            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor( IndexedColors.GREY_25_PERCENT.index);
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            style.setBorderBottom( CellStyle.BORDER_THIN );
            style.setBorderTop( CellStyle.BORDER_THIN);
            style.setBorderRight( CellStyle.BORDER_THIN );
            style.setBorderLeft( CellStyle.BORDER_THIN );

            // create header row
            Row header = sheet.createRow( 0 );
            
            cell = header.createCell( column++ );
            cell.setCellValue( "№" );
            cell.setCellStyle( style );
            cell = header.createCell( column++ );
            cell.setCellValue( messageSource.getMessage( "label.user.full_name", null, locale) );
            cell.setCellStyle( style );
            cell = header.createCell( column++ );
            cell.setCellValue( messageSource.getMessage( "label.assessment.name", null, locale) );
            cell.setCellStyle( style );
            cell = header.createCell( column++ );
            cell.setCellValue( messageSource.getMessage( "label.date.start", null, locale) );
            cell.setCellStyle( style );
            cell = header.createCell( column++ );
            cell.setCellValue( messageSource.getMessage( "label.data.status", null, locale) );
            cell.setCellStyle( style );
            cell = header.createCell( column++ );
            cell.setCellValue( messageSource.getMessage( "label.assessment.score", null, locale) );
            cell.setCellStyle( style );
            cell = header.createCell( column++ );
            cell.setCellValue( messageSource.getMessage( "label.asmt.task.count", null, locale));
            cell.setCellStyle( style );
            cell = header.createCell( column++ );
            cell.setCellValue( messageSource.getMessage( "label.asmt.task.respond", null, locale));
            cell.setCellStyle( style );
            cell = header.createCell( column++ );
            cell.setCellValue( messageSource.getMessage( "label.asmt.result.item.count.strue", null, locale) );
            cell.setCellStyle( style );
            cell = header.createCell( column++ );
            cell.setCellValue( messageSource.getMessage( "label.asmt.result.item.count.sfalse", null, locale) );
            cell.setCellStyle( style );
            
            Row courseRow = sheet.createRow( rowCount++ );
            courseRow.createCell( 0 ).setCellValue( rowCount - 1 );
            courseRow.createCell( 1 ).setCellValue( userDetails.getPerson().getLastName() + " "
                            + userDetails.getPerson().getFirstName() );
            courseRow.createCell( 2 ).setCellValue( result.getAssessment().getName() );
            courseRow.createCell( 3 ).setCellValue( StringUtils.dateToStringLong( result.getProcess().getStartDate() ) );
            courseRow.createCell( 4 ).setCellValue( "" );
            courseRow.createCell( 5 ).setCellValue( result.getScore() );
            courseRow.createCell( 6 ).setCellValue( result.getTaskCount() );
            courseRow.createCell( 7 ).setCellValue( result.getResponseCount() );
            courseRow.createCell( 8 ).setCellValue( result.getRightResponseCount());
            courseRow.createCell( 9 ).setCellValue( result.getResponseCount() - result.getRightResponseCount());
            //------------------------------------
            
            int taskCount = 1;
            column = 0;
            rowCount = 1;
            
            Sheet sheetRes = workbook.createSheet( messageSource.getMessage( "label.asmt.task.response.content", null, locale) );
            // create header row
            header = sheetRes.createRow( 0 );
            
            cell = header.createCell( column++ );
            cell.setCellValue( "№" );
            cell.setCellStyle( style );
            cell = header.createCell( column++ );
            cell.setCellValue( messageSource.getMessage( "label.asmt.task.item.content", null, locale) );
            cell.setCellStyle( style );
            cell = header.createCell( column++ );
            cell.setCellValue( messageSource.getMessage( "label.asmt.task.mode.type", null, locale) );
            cell.setCellStyle( style );
            cell = header.createCell( column++ );
            cell.setCellValue( messageSource.getMessage( "label.asmt.task.grade", null, locale) );
            cell.setCellStyle( style );
            cell = header.createCell( column++ );
            cell.setCellValue( messageSource.getMessage( "label.asmt.task.response", null, locale) );
            cell.setCellStyle( style );
            
            
            // Create data cells

            for ( Object[] obj : resultsPage.getContent() )
            {
                AssessmentTask task = (AssessmentTask)obj[1];
                AssessmentTaskDetail taskDetail = (AssessmentTaskDetail)obj[3];
                ProcessResponse taskResponse = (ProcessResponse)obj[0];

                courseRow = sheetRes.createRow( rowCount++ );
                
                if(taskId != task.getId())
                {
                    
                    courseRow.createCell( 0 ).setCellValue( taskCount++ );
                    
                    if(taskResponse.getGrade() <= 0)
                    {
                        cell = courseRow.createCell( 1 );
                        cell.setCellStyle( styleAnswer );
                        cell.setCellValue( task.getItemContent());
                        cell = courseRow.createCell( 2 );
                        cell.setCellStyle( styleAnswer );
                        cell.setCellValue( SystemUtils.getAttribute( "system.attrib.task.mode.type", task.getModeType() ));
                        cell = courseRow.createCell( 3 );
                        cell.setCellStyle( styleAnswer );
                        cell.setCellValue( taskResponse.getGrade());
                    }
                    else
                    {
                        courseRow.createCell( 1 ).setCellValue( task.getItemContent());
                        courseRow.createCell( 2 ).setCellValue( SystemUtils.getAttribute( "system.attrib.task.mode.type", task.getModeType() ));
                        courseRow.createCell( 3 ).setCellValue( taskResponse.getGrade());
                    }
                    
                }
                else
                {
                    courseRow.createCell( 0 ).setCellValue( "");
                    courseRow.createCell( 1 ).setCellValue( "");
                    courseRow.createCell( 2 ).setCellValue( "");
                    courseRow.createCell( 3 ).setCellValue( "");
                }
                
                courseRow.createCell( 4 ).setCellValue( taskDetail.getItemDetail() );
                
                taskId = task.getId();
            }
                  
        }
        catch ( Exception e )
        {
            logger.error( "********* Error generating XLS:" , e );
        }
    }

    @Override
    public void setMessageSource( MessageSource messageSource )
    {
        this.messageSource = messageSource;
    }

}