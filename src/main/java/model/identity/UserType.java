package model.identity;

public enum UserType
{
    System(1),
    Regular(2);
    
    private final static int groupId = 2;
    private int id;


    private UserType( int id)
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
