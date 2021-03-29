package ro.unibuc.project.common;

public class Location {

    private String country;
    private String city;
    private String streetName;
    private int streetNr;

    public Location(String country, String city, String streetName, int streetNr) {
        this.country = country;
        this.city = city;
        this.streetName = streetName;
        this.streetNr = streetNr;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getStreetNr() {
        return streetNr;
    }

    public void setStreetNr(int streetNr) {
        this.streetNr = streetNr;
    }

    @Override
    public String toString() {
        return "St." + streetName + " No." + streetNr + " " + city + ", " + country;
    }
}
