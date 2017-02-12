package common.utils;

import org.junit.Ignore;
import org.junit.Test;


@Ignore
//@RunWith(SpringJUnit4ClassRunner.class)
public class StringUtilsTest
{
    
    /*****************************************
     * 
     * */
    @Test
    public void minutesToDetailsTest( )
    {
        int minutes = 120;
        
        String details = StringUtils.minutesToDetails( minutes );
        
        System.out.println( details );
    }

}
