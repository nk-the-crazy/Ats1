package dao.api.assessment.task;

import org.springframework.data.jpa.repository.JpaRepository;

import model.assessment.task.AssessmentTask;

public interface AssessmentTaskDAO extends JpaRepository<AssessmentTask, Long>
{

}
