package common.utils;


import java.util.Date;
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

}
