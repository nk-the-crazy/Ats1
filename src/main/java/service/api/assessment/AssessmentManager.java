package service.api.assessment;


import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import model.assessment.Assessment;

public interface AssessmentManager
{

    Assessment getAssessmentFullDetails( long assessmentId );

    Assessment createAssessment( String name, Date startDate, Date endDate, int type );

    Page<Assessment> getAssessmentsByDetails( String assessmentName, Date startDate, Date endDate, short assessmentType,
            Pageable pageable );

    Assessment saveAssessment( Assessment assessment );

  
}
