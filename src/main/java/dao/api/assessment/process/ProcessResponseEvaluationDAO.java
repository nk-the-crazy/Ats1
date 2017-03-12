package dao.api.assessment.process;


import org.springframework.data.jpa.repository.JpaRepository;
import model.assessment.process.ProcessResponseRate;

public interface ProcessResponseEvaluationDAO extends JpaRepository<ProcessResponseRate, Long>
{

}
