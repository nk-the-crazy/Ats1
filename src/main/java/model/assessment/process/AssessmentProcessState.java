package model.assessment.process;

public enum AssessmentProcessState
{
    Ready(1),
    Started(2),
    Finished(4);

    private final static int groupId = 3;
    private int id;


    private AssessmentProcessState( int id )
    {
        this.id = id;
    }

    public int getGroupId()
    {
        return groupId;
    }

    public int getId()
    {
        return id;
    }
}
