package Entities;

public abstract class Item {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    protected Item(String id) {
        this.id = id;
    }
}
