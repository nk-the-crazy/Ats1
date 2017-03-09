package common.utils;


import java.util.Date;
import java.util.concurrent.TimeUnit;


import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import common.utils.system.SystemUtils;


public class StringUtils
{
    private static SimpleDateFormat shortDateFormatter = new SimpleDateFormat(
            SystemUtils.getSettings( "system.app.date.format.short" ) );

    private static SimpleDateFormat longDateFormatter = new SimpleDateFormat(
            SystemUtils.getSettings( "system.app.date.format.long" ) );

    /*****************************************
     * 
     * */
    public static Date stringToDate( String dateStr )
    {
        try
        {
            return shortDateFormatter.parse( dateStr );
        }
        catch ( ParseException e )
        {
            e.printStackTrace();
        }

        return new Date( System.currentTimeMillis() );
    }
    

    /*****************************************
     * 
     * */
    public static String dateToStringShort( Date date )
    {
        try
        {
            return shortDateFormatter.format( date );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        return shortDateFormatter. format(new Date( System.currentTimeMillis() ));
    }

    /*****************************************
     * 
     * */
    public static String dateToStringLong( Date date )
    {
        try
        {
            return longDateFormatter.format( date );
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        return longDateFormatter. format(new Date( System.currentTimeMillis() ));
    }

    
    /*****************************************
     * 
     * */
    public static String minutesToDetails( int minutess )
    {
        try
        {
            int hours = minutess / 60; //since both are ints, you get an int
            int minutes = minutess % 60;
            
            return String.format( "%02d:%02d:00", hours, minutes );
          
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        return "00:00:00";
    }

    /*****************************************
     * 
     * */
    public static String millisToDetails( int millis )
    {
        try
        {
            return String.format("%02d:%02d:%02d", 
                    TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) -  
                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), // The change is in this line
                    TimeUnit.MILLISECONDS.toSeconds(millis) - 
                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));   
          
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        return "00:00:00";
    }
    
    
    /*****************************************
     * 
     * */
    public static SimpleDateFormat getShortDateFormat()
    {
        return shortDateFormatter;
    }
    
    
    /* ********************************************
     * 
     * */
    public static String toUTF8(String str) 
    {
        try
        {    
            return new String(str.getBytes("UTF-8"));
        }
        catch ( UnsupportedEncodingException e )
        {
            // ignore
            return "";
        }
    }
    
    
    
    /* ********************************************
     * 
     * */
    public static String formatSpecial(String str) 
    {
        try
        {    
            int dot = str.trim().indexOf( "." );
            
            if(dot < 5 && dot >= 0)
            {
                str = str.substring( dot + 1, str.length());
            }
            str = str.replaceAll("\u00A0", "");
            str = str.trim();
            
            return str;
        }
        catch ( Exception e )
        {
            // ignore
            return "";
        }
    }
    
    
    
    /* ********************************************
     * 
     * */ 
    public static float getIdenticRatio(String source, String target)
    {
        try
        {
            int maxLength = (source.length() > target.length()) ? source.length():target.length();
            int distance = org.apache.commons.lang.StringUtils.getLevenshteinDistance( source, target );
            float ratio = 0;
            
            if(distance == 0)
                return 100;
            else
            {
                ratio = 100 - ((float)(distance * 100) / maxLength);
                
                return ratio < 70 ? 0 : ratio;
            }
        }
        catch(Exception e)
        {
            ///ignore error return 0
            return 0; 
        }
    } 
    
    

}
