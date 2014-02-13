package Interfaces;

import Entities.Actor;

public interface ActorRepoInterface extends ItemRepoInterface<Actor> {
    public boolean update(String id, String name, String company, String email, String phone, String address);
}
