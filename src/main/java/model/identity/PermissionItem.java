package model.identity;

public enum PermissionItem
{
    AssessmentTesting(1),
    IdentityManagement(2),
    GroupManagement(3),
    AssessmentManagement(4),
    AssessmentTaskManagement(5),
    ReportManagement(6);
    
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
