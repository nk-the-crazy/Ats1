package dao.api.assessment.process;

import org.springframework.data.jpa.repository.JpaRepository;
import model.assessment.process.ProcessResponseDetail;

public interface ProcessResponseDetailDAO extends JpaRepository<ProcessResponseDetail, Long>
{
   
}
