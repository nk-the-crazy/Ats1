package model.assessment.options;


//**********************************
public class TaskFormOptions
{
    private short complexity = 1;
    private short modeType = 1;
    private short number = 0;

    public short getComplexity()
    {
        return complexity;
    }

    public void setComplexity( short complexity )
    {
        this.complexity = complexity;
    }

    public short getModeType()
    {
        return modeType;
    }

    public void setModeType( short modeType )
    {
        this.modeType = modeType;
    }

    public short getNumber()
    {
        return number;
    }

    public void setNumber( short number )
    {
        this.number = number;
    }

}
