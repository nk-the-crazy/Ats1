package model.assessment.task;

public enum AssessmentTaskType
{
    SingleChoice(1),
    MultipleChoice(2),
    ShortAnswer(3),
    Essay(4);

    private final static int groupId = 3;
    private int id;


    private AssessmentTaskType( int id )
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
