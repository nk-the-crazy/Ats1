package service.impl;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.api.assessment.AssessmentDAO;
import model.assessment.Assessment;
import service.api.assessment.AssessmentManager;


@Service("localeManagerService")
@Transactional
public class AssessmentManagerImpl implements AssessmentManager
{
    // ---------------------------------
    private static final Logger logger = LoggerFactory.getLogger( AssessmentManagerImpl.class );
    // ---------------------------------

    @Autowired
    AssessmentDAO assessmentDAO;

    
    /**************************************************
     * 
     */
    @Override
    public Assessment createAssessment( String name, Date startDate, Date endDate, int type )
    {
        Assessment assessment = null;

        try
        {
            assessment = new Assessment();
            assessment.setName( name );
            assessment.setType( type ); 
            assessment.setStartDate( startDate );
            assessment.setEndDate( endDate );
        }
        catch ( Exception e )
        {
            logger.error( e.toString(), e );
        }

        return assessment;

    }
    
    /**************************************************
     * 
     */
    @Override
    public Assessment saveAssessment( Assessment assessment )
    {
        try
        {
            return assessmentDAO.save( assessment );
        }
        catch ( Exception e )
        {
            logger.error( " **** Error in saving assessment:", e );        
        }

        return null;
    }
    

    /**************************************************
     * 
     */
    @Override
    public Assessment getAssessmentFullDetails( long assessmentId )
    {
        return null;
    }
    

    /**************************************************
     * 
     */
    @Override
    public Page<Assessment> getAssessmentsByDetails( String assessmentName, Date startDate, Date endDate,
            short assessmentType, Pageable pageable )
    {
        return assessmentDAO.getByDetails(assessmentName, startDate, endDate,assessmentType, pageable);
    }

}
