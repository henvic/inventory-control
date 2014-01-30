package Entities;

public class Address {
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String extra;

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        String extra = this.getExtra();
        String output;

        output = "Endereço: \n" +
            this.getLine1() + "\n" +
            this.getLine2() + "\n" +
            this.getCity() + " - " + this.getState() + "\n";

        if (!extra.equals("")) {
            output += "Extra:\n" + this.getExtra() + "\n";
        }

        return output;
    }
}
