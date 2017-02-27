package dao.api.assessment.process;


import org.springframework.data.jpa.repository.JpaRepository;
import model.assessment.process.ProcessResponseEvaluation;

public interface ProcessResponseEvaluationDAO extends JpaRepository<ProcessResponseEvaluation, Long>
{

}
