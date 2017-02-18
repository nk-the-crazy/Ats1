package common.utils;


import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {    "file:src/main/webapp/WEB-INF/application-context.xml",
                                       "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml"})
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
