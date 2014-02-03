package Entities;

public class Actor extends Item {
    private String name;
    private String company;
    private String email;
    private String phone;
    private String address;
    private boolean buyer;
    private boolean seller;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isBuyer() {
        return buyer;
    }

    public void setBuyer(boolean buyer) {
        this.buyer = buyer;
    }

    public boolean isSeller() {
        return seller;
    }

    public void setSeller(boolean seller) {
        this.seller = seller;
    }

    public Actor(String id, String name, String company, String email, String phone, String address,
                 boolean buyer, boolean seller) {
        super(id);
        this.name = name;
        this.company = company;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.buyer = buyer;
        this.seller = seller;
    }
}
