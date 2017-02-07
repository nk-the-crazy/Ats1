package web.controller.assessment.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import service.api.assesment.task.AssessmentTaskManager;


@Controller
public class AssessmentTaskController
{
  //---------------------------------
    private static final Logger logger = LoggerFactory.getLogger(AssessmentTaskController.class);
    //---------------------------------

    @Autowired
    private AssessmentTaskManager taskManager;
}
