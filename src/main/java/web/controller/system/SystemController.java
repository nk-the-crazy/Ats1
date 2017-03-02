package web.controller.system;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import web.view.ModelView;


@Controller
public class SystemController
{
    //---------------------------------
    private static final Logger logger = LoggerFactory.getLogger(SystemController.class);
    //---------------------------------
    
    /*******************************************************
     * 
     * */
    @RequestMapping( "/changelocale.do" )
    public String changeLocale(@RequestParam( "locale" ) String localeType, HttpServletRequest request)
    {
        try   
        {
            //String localeStr = (String)request.getSession().getAttribute( "locale" );
            request.getSession().setAttribute( "locale", localeType );
        }
        catch(Exception e)
        {
            logger.error( "Error changing locale:" + e.toString(), e );
            //log and ignore
        }
        
        return  "redirect:" + request.getHeader("Referer");
    }
    


    /*******************************************************
     * 
     * */
    @RequestMapping( value={ "/system_error", "/error"} )
    public String getErrorView()
    {
        return ModelView.VIEW_SYSTEM_ERROR_PAGE;
    }
    

    /*******************************************************
     * 
     * */
    @RequestMapping( value="/access_error_403" )
    public String getAccessErrorView(Model model)
    {
        model.addAttribute( "errorData", "message.error.access.denied");
        return ModelView.VIEW_SYSTEM_ERROR_PAGE;
    }
    

}
