package model.common;

public enum DataStatus
{
    Active(1),
    Diabled(2);

    private final static int groupId = 2;
    private int id;


    private DataStatus( int id )
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
