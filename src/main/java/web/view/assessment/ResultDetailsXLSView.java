package web.view.assessment;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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


public class ResultDetailsXLSView extends AbstractXlsxView  implements MessageSourceAware
{
    //---------------------------------
    private static final Logger logger = LoggerFactory.getLogger(ResultDetailsXLSView.class);
    //---------------------------------
    
    private MessageSource messageSource;
    private Locale locale = LocaleContextHolder.getLocale() ;
    
    @Override
    protected void buildExcelDocument( Map<String, Object> model, Workbook workbook, HttpServletRequest request,
                    HttpServletResponse response ) throws Exception
    {

        try
        {
            @SuppressWarnings("unchecked")
            Page<Object[]> resultsPage = (Page<Object[]>) model.get( "resultsPage" );

            // change the file name
            response.setHeader( "Content-Disposition", "attachment; filename=\"my-xls-file.xlsx\"" );

            // create excel xls sheet
            Sheet sheet = workbook.createSheet( messageSource.getMessage( "label.page.report_result_list.title", null, null) );

            // create header row
            Row header = sheet.createRow( 0 );
            header.createCell( 0 ).setCellValue( "№" );
            header.createCell( 1 ).setCellValue( messageSource.getMessage( "label.user.full_name", null, locale) );
            header.createCell( 2 ).setCellValue( messageSource.getMessage( "label.assessment.name", null, locale) );
            header.createCell( 3 ).setCellValue( messageSource.getMessage( "label.date.start", null, locale) );
            header.createCell( 4 ).setCellValue( messageSource.getMessage( "label.data.status", null, locale) );
            header.createCell( 5 ).setCellValue( messageSource.getMessage( "label.assessment.score", null, locale) );
            header.createCell( 6 ).setCellValue( messageSource.getMessage( "label.asmt.task.respond", null, locale));
            header.createCell( 7 ).setCellValue( messageSource.getMessage( "label.asmt.result.item.count.all", null, locale) );

            // Create data cells
            int rowCount = 1;

            for ( Object[] obj : resultsPage.getContent() )
            {
                AssessmentProcess process = (AssessmentProcess) obj[0];

                long responseCount = (long) obj[5];
                long rightResponseCount = (long) obj[6];
                double score = (double) obj[7];

                Row courseRow = sheet.createRow( rowCount++ );
                courseRow.createCell( 0 ).setCellValue( rowCount - 1 );
                courseRow.createCell( 1 ).setCellValue( process.getUser().getPerson().getLastName() + " "
                                + process.getUser().getPerson().getFirstName() );
                courseRow.createCell( 2 ).setCellValue( process.getAssessment().getName() );
                courseRow.createCell( 3 ).setCellValue( StringUtils.dateToStringLong( process.getStartDate() ) );
                courseRow.createCell( 4 ).setCellValue( "" );
                courseRow.createCell( 5 ).setCellValue( score );
                courseRow.createCell( 7 ).setCellValue( responseCount );
                courseRow.createCell( 7 ).setCellValue( rightResponseCount + " - " + (responseCount - rightResponseCount) );
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