package model.assessment.task;

public enum AssessmentTaskOrdering
{
    Static(1),
    Orderable(2);

    private final static int groupId = 3;
    private int id;


    private AssessmentTaskOrdering( int id )
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
