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
import model.assessment.process.AssessmentProcess;


public class ResultsXLSView extends AbstractXlsxView  implements MessageSourceAware
{
    //---------------------------------
    private static final Logger logger = LoggerFactory.getLogger(ResultsXLSView.class);
    //---------------------------------
    
    private MessageSource messageSource;

    
    @Override
    protected void buildExcelDocument( Map<String, Object> model, Workbook workbook, HttpServletRequest request,
                    HttpServletResponse response ) throws Exception
    {

        Locale locale = LocaleContextHolder.getLocale() ;
        
        try
        {
            int rowCount = 1;
            int column = 0;
            Cell cell = null;
            
            @SuppressWarnings("unchecked")
            Page<Object[]> resultsPage = (Page<Object[]>) model.get( "resultsPage" );

            response.setHeader( "Content-Disposition", "attachment; filename=\"results.xlsx\"" );

            // create excel xls sheet
            Sheet sheet = workbook.createSheet( messageSource.getMessage( "label.page.report_result_list.title", null, locale) );
            
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
            
            // Create data cells
            rowCount = 1;

            for ( Object[] obj : resultsPage.getContent() )
            {
                AssessmentProcess process = (AssessmentProcess) obj[0];

                long taskCount = process.getAssessment().getTaskCount();
                long responseCount = (long) obj[4];
                long rightResponseCount = (long) obj[5];
                double score = (double) obj[6];

                Row courseRow = sheet.createRow( rowCount++ );
                courseRow.createCell( 0 ).setCellValue( rowCount - 1 );
                courseRow.createCell( 1 ).setCellValue( process.getUser().getPerson().getLastName() + " "
                                + process.getUser().getPerson().getFirstName() );
                courseRow.createCell( 2 ).setCellValue( process.getAssessment().getName() );
                courseRow.createCell( 3 ).setCellValue( StringUtils.dateToStringLong( process.getStartDate() ) );
                courseRow.createCell( 4 ).setCellValue( "" );
                courseRow.createCell( 5 ).setCellValue( score );
                courseRow.createCell( 6 ).setCellValue( taskCount );
                courseRow.createCell( 7 ).setCellValue( responseCount );
                courseRow.createCell( 8 ).setCellValue( rightResponseCount);
                courseRow.createCell( 9 ).setCellValue( responseCount - rightResponseCount);
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