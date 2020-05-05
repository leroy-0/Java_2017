package server;

public class Contract {

    private int             value;
    private String          type;

    Contract(int value, String type)
    {
        this.value = value;
        this.type = type;
    }

    public int          getValue()
    {
        return (this.value);
    }

    public String       getType()
    {
        return (this.type);
    }
}
