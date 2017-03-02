package common.utils;

import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class IdenticalRatioTest
{
    /*****************************************
     * 
     * */
    @Test
    public void testIdenticalRatio()
    {
        String source = "Sofffff";
        String target = "Source";
        
        
        int maxLength = (source.length() > target.length()) ? source.length():target.length();
        int distance = StringUtils.getLevenshteinDistance( source, target );
        float ratio = 0;
        
        if(distance == 0)
            ratio = 100;
        else
            ratio = (100 - ((float)(distance * 100) / maxLength));
        
        System.out.println("MaxLength -  distance:" + maxLength + "-"+distance );
        System.out.println("Ratio for s-t:" + source + "-"+target + " is :" + ratio);
        
        assertTrue(distance > 0);

    }
}
