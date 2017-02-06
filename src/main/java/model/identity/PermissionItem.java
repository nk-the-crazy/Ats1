package model.identity;

public enum PermissionItem
{
    IdentityManagement(1),
    GroupManagement(2),
    AssessmentManagement(3),
    TaskManagement(4),
    ReportManagement(5);
    
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
