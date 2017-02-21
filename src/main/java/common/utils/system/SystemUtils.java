package common.utils.system;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;


@Component
@Scope("singleton")
public class SystemUtils
{
    private static ResourceBundleMessageSource settingsBundle;
    private static MessageSource messagesBundle;
    
    @Autowired
    private MessageSource messageSource;
    
        
    /* ********************************************
     * 
     * */
    @PostConstruct
    void init() 
    {
        settingsBundle = new ResourceBundleMessageSource();
        settingsBundle.setBasename( "system" );
        messagesBundle = messageSource;
    }
    
    /* ********************************************
     * 
     * */
    public static String getSettings(String key) 
    {
        try
        {
            return settingsBundle.getMessage( key, null, null );
        }
        catch ( Exception e )
        {
            return "Unresolved key: " + key;
        }
    
    }
    
    /* ********************************************
     * 
     * */
    public static String getMessage(String key, Locale locale) 
    {
        try
        { 
            return messagesBundle.getMessage( key, null, locale );
        }
        catch ( Exception e )
        {
            return "Unresolved key: " + key;
        }
    
    }

    /* ********************************************
     * 
     * */
    public static String getMessage(String key) 
    {
        try
        { 
            return messagesBundle.getMessage( key, null, Locale.getDefault() );
        }
        catch ( Exception e )
        {
            return "Unresolved key: " + key;
        }
    
    }

    /* ********************************************
     * 
     * */
    private static List<String> getAttributes(String key, Locale locale) 
    {
        try
        { 
            String strList = messagesBundle.getMessage( key, null, locale);
            
            if(!Strings.isNullOrEmpty( strList )) 
            {
                return Arrays.asList(strList.split( "," ));
            }
            else 
            {
                return Collections.emptyList();
            }
        }
        catch ( Exception e )
        {
            return Collections.emptyList();
        }
    
    }
    
    /* ********************************************
     * 
     * */
    public static List<String> getAttributes(String key, String localeStr) 
    {
        return Strings.isNullOrEmpty( localeStr )? 
                getAttributes( key, Locale.getDefault()) :  
                    getAttributes( key, new Locale(localeStr));
    }
    
    
    /* ********************************************
     * 
     * */
    private static String getAttribute(String key, int index, Locale locale) 
    {
        try
        { 
            String strList = messagesBundle.getMessage( key, null, locale);
            
            if(!Strings.isNullOrEmpty( strList )) 
            {
                return strList.split( "," )[index-1];
            }
            else 
            {
                return "";
            }
        }
        catch ( Exception e )
        {
            return "";
        }
    
    }
    
    
    /* ********************************************
     * 
     * */
    public static String getAttribute(String key, int index, String localeStr) 
    {
        return Strings.isNullOrEmpty( localeStr )? 
                getAttribute( key, index, Locale.getDefault()) :  
                    getAttribute( key, index, new Locale(localeStr));
    }
    
    
    
    /* ********************************************
     * 
     * */
    public static int getTimingLatecy() 
    {
        try
        { 
            String str = messagesBundle.getMessage( "system.app.timing.latecy", null, null);
            
            
            return Integer.parseInt( str );
        }
        catch ( Exception e )
        {
            return 0;
        }
    }

}