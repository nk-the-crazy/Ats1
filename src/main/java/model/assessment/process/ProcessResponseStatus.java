package model.assessment.process;


public enum ProcessResponseStatus
{
    NoResponse(1),
    Responded(2),
    Skipped(3);

    private final static int groupId = 3;
    private int id;


    private ProcessResponseStatus( int id )
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
