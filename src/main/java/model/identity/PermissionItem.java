package model.identity;

public enum PermissionItem
{
    IdentityManagement(1),
    AssessmentManagement(2),
    QuestionManagement(3),
    ReportManagement(4);
    
    private final static int groupId = 2;
    private int id;


    private PermissionItem(  int id)
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
