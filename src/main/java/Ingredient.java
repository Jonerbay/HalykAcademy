enum Type{
    RAW, BOILED, FRIED, SHREDDED
}
public class Ingredient {
    private String name;
    public Type type;
    public Ingredient(String name) {
        this.name = name;
        type = Type.RAW;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

