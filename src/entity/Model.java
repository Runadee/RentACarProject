package entity;

public class Model {
    private int id;
    private int brand_id;
    private String name;
    private Type type;
    private String string;
    private Fuel fuel;
    private Gear gear;
    private Brand brand;

    public enum Fuel {
        GASOLINE,
        LPG,
        ELECTRIC,
        DIESEL
    }

    public enum Gear {
        MANUEL,
        AUTO
    }

    public enum Type {
        SEDAN,
        HATCHBACK
    }

    public Model() {

    }
}
