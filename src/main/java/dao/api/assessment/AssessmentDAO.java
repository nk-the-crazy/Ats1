package dao.api.assessment;

import org.springframework.data.jpa.repository.JpaRepository;

import model.assessment.Assessment;


public interface AssessmentDAO extends JpaRepository<Assessment, Long>
{

}
