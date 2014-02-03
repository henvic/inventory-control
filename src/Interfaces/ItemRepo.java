package Interfaces;

public interface ItemRepo<type> {
    public boolean add(type item);
    public boolean remove(String id);
    public type get(String id);
    public boolean has(String id);
}
