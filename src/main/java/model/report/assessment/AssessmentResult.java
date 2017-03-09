package model.report.assessment;


import model.assessment.Assessment;
import model.assessment.process.AssessmentProcess;


public class AssessmentResult
{
    private AssessmentProcess process = null;
    private Assessment assessment = null;

    private long responseCount = 0;
    private long rightResponseCount = 0;
    private long wrongResponseCount = 0;
    private long taskCount = 0;
    private long userId = 0;
    private double score = 0;

    public AssessmentResult(AssessmentProcess process, long taskCount, long responseCount, long rightResponseCount,
                    double score)
    {
        this.responseCount = responseCount;
        this.rightResponseCount = rightResponseCount;
        this.score = score;
        this.taskCount = taskCount;
        this.process = process;

        if ( process != null && process.getAssessment() != null )
        {
            assessment = process.getAssessment();
            assessment.getId();
        }

    }

    public AssessmentResult(AssessmentProcess process,
                            long userId,
                            long taskCount, 
                            long responseCount, 
                            long rightResponseCount,
                            double score)
    {
        this.responseCount = responseCount;
        this.rightResponseCount = rightResponseCount;
        this.score = score;
        this.taskCount = taskCount;
        this.process = process;
        this.userId = userId;
        
        if ( process != null && process.getAssessment() != null )
        {
            assessment = process.getAssessment();
            assessment.getId();
        }

    }

    public AssessmentProcess getProcess()
    {
        return process;
    }

    public void setProcess( AssessmentProcess process )
    {
        this.process = process;
    }

    public long getResponseCount()
    {
        return responseCount;
    }

    public void setResponseCount( long responseCount )
    {
        this.responseCount = responseCount;
    }

    public long getRightResponseCount()
    {
        return rightResponseCount;
    }

    public void setRightResponseCount( long rightResponseCount )
    {
        this.rightResponseCount = rightResponseCount;
    }

    public long getWrongResponseCount()
    {
        return wrongResponseCount;
    }

    public void setWrongResponseCount( long wrongResponseCount )
    {
        this.wrongResponseCount = wrongResponseCount;
    }

    public long getTaskCount()
    {
        return taskCount;
    }

    public void setTaskCount( long taskCount )
    {
        this.taskCount = taskCount;
    }

    public double getScore()
    {
        return score;
    }

    public void setScore( double score )
    {
        this.score = score;
    }

    public Assessment getAssessment()
    {
        return assessment;
    }

    public void setAssessment( Assessment assessment )
    {
        this.assessment = assessment;
    }

    public long getUserId()
    {
        return userId;
    }

    public void setUserId( long userId )
    {
        this.userId = userId;
    }
    
    

}
