package model.assessment;

public enum EvaluationMethod
{
    TrueAnswers(1),
    AnswerRatio(2);

    private final static int groupId = 13;
    private int id;


    private EvaluationMethod( int id )
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
