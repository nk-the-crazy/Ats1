package web.common.view.xls;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import common.utils.StringUtils;
import model.assessment.process.AssessmentProcess;

@Component("viewXLSAssessmentResults")
public class AssessmentResults extends AbstractXlsView 
{
    @Override
    protected void buildExcelDocument( Map<String, Object> model, Workbook workbook, HttpServletRequest request,
                    HttpServletResponse response ) throws Exception
    {

        @SuppressWarnings("unchecked")
        Page<Object[]> resultsPage = (Page<Object[]>) model.get( "resultsPage" );
        
        // change the file name
        response.setHeader("Content-Disposition", "attachment; filename=\"my-xls-file.xls\"");

        // create excel xls sheet
        Sheet sheet = workbook.createSheet("Assessment Results");

        // create header row
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("№");
        header.createCell(1).setCellValue("Ф.И.О");
        header.createCell(2).setCellValue("Наимен. Тестирования");
        header.createCell(3).setCellValue("Дата начала");
        header.createCell(4).setCellValue("Статус");
        header.createCell(5).setCellValue("Набранные баллы");
        header.createCell(6).setCellValue("Пройдено вопросов");
        header.createCell(7).setCellValue("Кол-во. Прав-Неправ. ответов");


        // Create data cells
        int rowCount = 1;
        
        for (Object []obj : resultsPage.getContent())
        {
            AssessmentProcess process = (AssessmentProcess) obj[0];
            
            float responseCount = (float) obj[5];
            float rightResponseCount = (float) obj[6];
            float score = (float) obj[7];
            
            Row courseRow = sheet.createRow(rowCount++);
            courseRow.createCell(0).setCellValue(rowCount - 1);
            courseRow.createCell(1).setCellValue(process.getUser().getPerson().getLastName() +" "+process.getUser().getPerson().getFirstName());
            courseRow.createCell(2).setCellValue(process.getAssessment().getName());
            courseRow.createCell(3).setCellValue(StringUtils.dateToStringLong( process.getStartDate() ));
            courseRow.createCell(4).setCellValue("");
            courseRow.createCell(5).setCellValue(score);
            courseRow.createCell(7).setCellValue(responseCount);
            courseRow.createCell(7).setCellValue(rightResponseCount + " - " + (responseCount - rightResponseCount));
            
            
        }
    }

}