package lk.ijse.aquarium.to;

import java.sql.Time;

public class Dilivary {
    private String id;
    private String time;
    private String address;

    public Dilivary(String id) {}

    public Dilivary(String id, String time, String address) {
        this.id = id;
        this.time = time;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Dilivary{" +
                "id='" + id + '\'' +
                ", time=" + time +
                ", address='" + address + '\'' +
                '}';
    }
}
