package web.model.assessment.process;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.assessment.task.AssessmentTask;

import static org.junit.Assert.assertTrue;

public class ProcessResponseDTOTest
{
    // ---------------------------------
    private static final Logger logger = LoggerFactory.getLogger( ProcessResponseDTOTest.class );
    // ---------------------------------
    
    @Test
    public void testDTO()
    {
        AssessmentTask tsk = new AssessmentTask();
        tsk.setId( 100 );
        tsk.setModeType( 1 );
        
        ProcessResponseDTO dto = new ProcessResponseDTO(tsk, null, null);
        
        try
        {
            String json = new ObjectMapper().writeValueAsString(dto);
            
            logger.info( "info {}" , json );
            
            assertTrue(json.contains( "taskModeTypeName" ));
            assertTrue(json.contains( "taskId" ));
            
        }
        catch ( JsonProcessingException e )
        {
            e.printStackTrace();
        }
    }
}
