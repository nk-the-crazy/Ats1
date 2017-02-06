package model.common;

public enum DataValue
{
    Primary(1),
    Secondary(2);

    private final static int groupId = 3;
    private int id;


    private DataValue( int id )
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
