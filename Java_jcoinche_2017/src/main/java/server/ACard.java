package server;

public abstract class ACard {
    private String   name;
    private int      value;
    private boolean  asset;

    ACard(String name, int value, boolean asset)
    {
        this.name = name;
        this.value = value;
        this.setAsset(asset);
    }

    public String getName()
    {
        return (name);
    }

    public int getValue()
    {
        return (value);
    }

    public boolean getAsset()
    {
        return (asset);
    }

    public void     setAsset(boolean asset) {
        this.asset = asset;
        if (this.asset == true)
        {
            if (this.name.equals("Valet"))
                this.value = 20;
            else if (this.name.equals("9"))
                this.value = 14;
        }
        else
        {
            if (this.name.equals("Valet"))
                this.value = 2;
            else if (this.name.equals("9"))
                this.value = 0;
        }
    }
}
