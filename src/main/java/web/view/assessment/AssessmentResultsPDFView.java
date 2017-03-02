package web.view.assessment;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.data.domain.Page;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import common.utils.StringUtils;
import model.assessment.process.AssessmentProcess;
import web.view.AbstractITextPdfView;


/**
 *
 */
public class AssessmentResultsPDFView extends AbstractITextPdfView  implements MessageSourceAware
{
    //---------------------------------
    private static final Logger logger = LoggerFactory.getLogger(AssessmentResultsPDFView.class);
    //---------------------------------
    
    private MessageSource messageSource;
    
    @Override
    protected void buildPdfDocument( Map<String, Object> model, Document doc, PdfWriter writer,
                    HttpServletRequest request, HttpServletResponse response ) throws Exception
    {
        try
        {
            @SuppressWarnings("unchecked")
            Page<Object[]> resultsPage = (Page<Object[]>) model.get( "resultsPage" );
            
            doc.setPageSize( PageSize.A4.rotate() );
            doc.open();
            
            BaseFont bf;
            bf = BaseFont.createFont("/fonts/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED) ;
            bf.getCodePagesSupported();
            
            Font f1 = new Font(bf,13);
            String title = messageSource.getMessage( "label.page.report_result_list.title", null, null);
            doc.add(new Paragraph(title, f1) );

            PdfPTable table = new PdfPTable( 8 );
            table.setWidthPercentage( 100.0f );
            table.setWidths( new float[] { 1.0f, 5.0f, 5.0f, 4.0f, 2.0f, 2.0f, 2.0f, 2.0f } );
            table.setSpacingBefore( 10 );

            Font f2 = new Font(bf,11);
            f2.setColor( BaseColor.WHITE );

            // define table header cell
            PdfPCell cell = new PdfPCell();
            cell.setBackgroundColor( BaseColor.LIGHT_GRAY );
            cell.setPadding( 5 );
            
            cell.setPhrase( new Phrase("№",f2));
            table.addCell( cell );
            cell.setPhrase( new Phrase( messageSource.getMessage( "label.user.full_name", null, null),f2));
            table.addCell( cell );
            cell.setPhrase( new Phrase( messageSource.getMessage( "label.assessment.name", null, null),f2));
            table.addCell( cell );
            cell.setPhrase( new Phrase( messageSource.getMessage( "label.date.start", null, null),f2));
            table.addCell( cell );
            cell.setPhrase( new Phrase( messageSource.getMessage( "label.data.status", null, null),f2 ));
            table.addCell( cell );
            cell.setPhrase( new Phrase( messageSource.getMessage( "label.assessment.score", null, null),f2 ));
            table.addCell( cell );
            cell.setPhrase( new Phrase( messageSource.getMessage( "label.asmt.task.respond", null, null),f2));
            table.addCell( cell );
            cell.setPhrase( new Phrase( messageSource.getMessage( "label.asmt.result.item.count.all", null, null),f2 ));
            table.addCell( cell );

            // Create data cells
            int rowCount = 1;
            f2.setColor( BaseColor.BLACK );
            
            for (Object []obj : resultsPage.getContent())
            {
                AssessmentProcess process = (AssessmentProcess) obj[0];
                
                long responseCount = (long) obj[5];
                long rightResponseCount = (long) obj[6];
                double score = (double) obj[7];

                table.addCell(Integer.toString(rowCount ++ ));
                table.addCell(new Phrase(process.getUser().getPerson().getLastName() +" "+
                              process.getUser().getPerson().getFirstName(),f2));
                table.addCell(new Phrase(process.getAssessment().getName(),f2));
                table.addCell(StringUtils.dateToStringLong( process.getStartDate() ));
                table.addCell("");
                table.addCell(Double.toString( score));
                table.addCell(Long.toString( responseCount));
                table.addCell(Long.toString( rightResponseCount) + " - " + 
                              Long.toString((responseCount - rightResponseCount)));

            }
  
            doc.add( table );
        }
        catch ( Exception e )
        {
            logger.error( "********* Error generating PDF:" , e );
        }

    }

    @Override
    public void setMessageSource( MessageSource messageSource )
    {
        this.messageSource = messageSource;
    }

}