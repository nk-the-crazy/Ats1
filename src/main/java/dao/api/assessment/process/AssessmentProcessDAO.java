package dao.api.assessment.process;


import org.springframework.data.jpa.repository.JpaRepository;
import model.assessment.process.AssessmentProcess;


public interface AssessmentProcessDAO extends JpaRepository<AssessmentProcess, Long>
{

}
