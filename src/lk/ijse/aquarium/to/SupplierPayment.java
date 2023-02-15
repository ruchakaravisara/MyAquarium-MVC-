package lk.ijse.aquarium.to;

public class SupplierPayment {
    private String id;
    private double value;
    private String sid;

    public SupplierPayment(String id) {}

    public SupplierPayment(String id, double value, String sid) {
        this.id = id;
        this.value = value;
        this.sid = sid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    @Override
    public String toString() {
        return "SupplierPayment{" +
                "id='" + id + '\'' +
                ", value=" + value +
                ", sid='" + sid + '\'' +
                '}';
    }
}
