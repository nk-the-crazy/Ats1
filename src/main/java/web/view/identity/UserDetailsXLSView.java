package web.view.identity;

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

import common.utils.StringUtils;
import common.utils.system.SystemUtils;
import model.contact.Address;
import model.contact.Contact;
import model.identity.User;
import model.person.Person;
import model.person.PersonDetails;
import web.model.identity.UserExportParams;

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

        Locale locale = LocaleContextHolder.getLocale();
        
        try
        {
            int rowCount = 1;
            int column = 0;
            Cell cell = null;
            
            @SuppressWarnings("unchecked")
            List<User> userList  = (List<User>) model.get( "userDetailsList" );
            UserExportParams exportParams = (UserExportParams) model.get( "userExportParams" );

            response.setHeader( "Content-Disposition", "attachment; filename=\"users.xlsx\"" );

            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor( IndexedColors.GREY_25_PERCENT.index);
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);
            
            style.setBorderBottom( CellStyle.BORDER_THIN );
            style.setBorderTop( CellStyle.BORDER_THIN);
            style.setBorderRight( CellStyle.BORDER_THIN );
            style.setBorderLeft( CellStyle.BORDER_THIN );
            
            Sheet sheet = workbook.createSheet( messageSource.getMessage( "label.page.report_result_list.title", null, locale) );
            
            Row header = sheet.createRow( 0 );
            cell = header.createCell( column++ );
            cell.setCellValue( "№" );
            cell.setCellStyle( style );
            cell = header.createCell( column++ );
            cell.setCellValue( messageSource.getMessage( "label.user.login", null, locale) );
            cell.setCellStyle( style );
            cell = header.createCell( column++ );
            cell.setCellValue( messageSource.getMessage( "label.user.last_name", null, locale) );
            cell.setCellStyle( style );
            cell = header.createCell( column++ );
            cell.setCellValue( messageSource.getMessage( "label.user.first_name", null, locale) );
            cell.setCellStyle( style );
            cell = header.createCell( column++ );
            cell.setCellValue( messageSource.getMessage( "label.user.middle_name", null, locale) );
            cell.setCellStyle( style );
            
            if(exportParams.isPassport())
            {
                cell = header.createCell( column++ );
                cell.setCellValue( messageSource.getMessage( "label.user.passport", null, locale) );
                cell.setCellStyle( style );
                cell = header.createCell( column++ );
                cell.setCellValue( messageSource.getMessage( "label.user.passport.date.valid", null, locale) );
                cell.setCellStyle( style );
                cell = header.createCell( column++ );
                cell.setCellValue( messageSource.getMessage( "label.user.passport.date.issued", null, locale) );
                cell.setCellStyle( style );
                cell = header.createCell( column++ );
                cell.setCellValue( messageSource.getMessage( "label.user.passport.issued_by", null, locale) );
                cell.setCellStyle( style );
            }

            if(exportParams.isContacts())
            {
                //----------- Contacts ----------------------
                cell = header.createCell( column++ );
                cell.setCellValue( messageSource.getMessage( "label.contacts.email", null, locale) );
                cell.setCellStyle( style );
                cell = header.createCell( column++ );
                cell.setCellValue( messageSource.getMessage( "label.contacts.phone", null, locale) );
                cell.setCellStyle( style );
                cell = header.createCell( column++ );
                cell.setCellValue( messageSource.getMessage( "label.contacts.secondary", null, locale) );
                cell.setCellStyle( style );
                
                //----------- Address  ----------------------
                cell = header.createCell( column++ );
                cell.setCellValue( messageSource.getMessage( "label.address.country", null, locale) );
                cell.setCellStyle( style );
                cell = header.createCell( column++ );
                cell.setCellValue( messageSource.getMessage( "label.address.region", null, locale) );
                cell.setCellStyle( style );
                cell = header.createCell( column++ );
                cell.setCellValue( messageSource.getMessage( "label.address.city", null, locale) );
                cell.setCellStyle( style );
                cell = header.createCell( column++ );
                cell.setCellValue( messageSource.getMessage( "label.address.primary", null, locale) );
                cell.setCellStyle( style );
                cell = header.createCell( column++ );
                cell.setCellValue( messageSource.getMessage( "label.address.secondary", null, locale) );
                cell.setCellStyle( style );
            }
            
            if(exportParams.isOrganization() )
            {
                cell = header.createCell( column++ );
                cell.setCellValue( messageSource.getMessage( "label.organization", null, locale) );
                cell.setCellStyle( style );
                cell = header.createCell( column++ );
                cell.setCellValue( messageSource.getMessage( "label.user.activity", null, locale) );
                cell.setCellStyle( style );
            }

            if(exportParams.isEducation() )
            {
                cell = header.createCell( column++ );
                cell.setCellValue( messageSource.getMessage( "label.education.institution", null, locale) );
                cell.setCellStyle( style );
                cell = header.createCell( column++ );
                cell.setCellValue( messageSource.getMessage( "label.education.qualification", null, locale) );
                cell.setCellStyle( style );
                cell = header.createCell( column++ );
                cell.setCellValue( messageSource.getMessage( "label.education.certificate.number", null, locale) );
                cell.setCellStyle( style );
                cell = header.createCell( column++ );
                cell.setCellValue( messageSource.getMessage( "label.education.certificate.date", null, locale) );
                cell.setCellStyle( style );
            }
            // ------------- Create data cells --------------------
            rowCount = 1;
            
            
            for ( User user : userList)
            {
                column = 0;
                Person person = user.getPerson();
                PersonDetails detail = person.getDetail();
                                
                Row courseRow = sheet.createRow( rowCount++ );
                courseRow.createCell( column++ ).setCellValue( rowCount - 1 );
                courseRow.createCell( column++ ).setCellValue( user.getUserName() );
                courseRow.createCell( column++ ).setCellValue( person.getLastName() );
                courseRow.createCell( column++ ).setCellValue( person.getFirstName() );
                courseRow.createCell( column++ ).setCellValue( person.getMiddleName() );
                
                if(exportParams.isPassport())
                {
                    if(detail != null)
                    {
                        courseRow.createCell( column++ ).setCellValue( detail.getPassportSerial() + " " + detail.getPassportNumber());
                        courseRow.createCell( column++ ).setCellValue( StringUtils.dateToStringShort( detail.getPassportValidDate() ));
                        courseRow.createCell( column++ ).setCellValue( StringUtils.dateToStringShort( detail.getPassportIssuedDate() ));
                        courseRow.createCell( column++ ).setCellValue( detail.getPassportIssuedBy());
                    }
                    else
                        column +=4; 
                }
                
                if(exportParams.isContacts() )
                {
                    Address address = person.getAddress();
                    Contact contact = person.getContact();
                    
                    if(address != null)
                    {
                        courseRow.createCell( column++ ).setCellValue( contact.getEmail());
                        courseRow.createCell( column++ ).setCellValue( contact.getPhone());
                        courseRow.createCell( column++ ).setCellValue( contact.getSecondaryContacts());
                        courseRow.createCell( column++ ).setCellValue( SystemUtils.getAttribute( "system.attrib.address.country", address.getCountryId() ));
                        courseRow.createCell( column++ ).setCellValue( SystemUtils.getAttribute( "system.attrib.address.region.2", address.getRegionId() ));
                    }
                    else
                        column +=5; 
                    
                    if(contact != null)
                    {
                        courseRow.createCell( column++ ).setCellValue( address.getCity());
                        courseRow.createCell( column++ ).setCellValue( address.getPrimaryAddress());
                        courseRow.createCell( column++ ).setCellValue( address.getSecondaryAddress());
                    }
                    else
                        column +=3; 

                }
                
                if(exportParams.isOrganization() )
                {
                    if(person.getOrganization() != null)
                        courseRow.createCell( column++ ).setCellValue( person.getOrganization().getName());
                    else
                        column +=1; 
                    
                    if(detail != null)
                        courseRow.createCell( column++ ).setCellValue( detail.getActivity());
                    else
                        column +=1; 
                }
                
                if(exportParams.isEducation() )
                {
                    if(detail != null)
                    {
                        courseRow.createCell( column++ ).setCellValue( detail.getEdcInstitution());   
                        courseRow.createCell( column++ ).setCellValue( detail.getQualification());   
                        courseRow.createCell( column++ ).setCellValue( detail.getEdcCertificateNumber());   
                        courseRow.createCell( column++ ).setCellValue( StringUtils.dateToStringShort( detail.getEdcCertificateDate()));
                    }   
                }
            }
        }
        catch ( Exception e )
        {
            logger.error( "********* Error generating User XLS:" , e );
        }
    }

    @Override
    public void setMessageSource( MessageSource messageSource )
    {
        this.messageSource = messageSource;
    }

}