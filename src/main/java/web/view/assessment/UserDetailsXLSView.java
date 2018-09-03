package web.view.assessment;

import java.util.List;
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
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import model.assessment.Assessment;
import model.identity.User;
import model.person.Person;


public class UserDetailsXLSView extends AbstractXlsxView  implements MessageSourceAware
{
    //---------------------------------
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsXLSView.class);
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
            List<Object[]> asmtUsersList = (List<Object[]>) model.get( "asmtUsersList" );

            response.setHeader( "Content-Disposition", "attachment; filename=\"user_details.xlsx\"" );

            // create excel xls sheet
            Sheet sheet = workbook.createSheet( messageSource.getMessage( "label.user.list", null, locale) );
            
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
            cell.setCellValue( messageSource.getMessage( "label.user.token", null, locale) );
            cell.setCellStyle( style );
            
            // Create data cells
            rowCount = 1;

            for ( Object[] obj : asmtUsersList )
            {
                User user = (User) obj[0];
                Person person = (Person) obj[1];
                Assessment asmt = (Assessment) obj[2];

                Row courseRow = sheet.createRow( rowCount++ );
                courseRow.createCell( 0 ).setCellValue( rowCount - 1 );
                courseRow.createCell( 1 ).setCellValue( person.getLastName() + " " + person.getFirstName() );
                courseRow.createCell( 2 ).setCellValue( asmt.getName() );
                
                String token = user.getToken() + "-" + asmt.getId();
                courseRow.createCell( 3 ).setCellValue( token.toUpperCase() );
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