package model.assessment.process;

public enum ProcessState
{
    Ready(1),
    Started(2),
    Finished(3);

    private final static int groupId = 3;
    private int id;


    private ProcessState( int id )
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
