package lk.ijse.aquarium.to;

public class SupplierItem {
    private String Sid;
    private String name;
    private String address;
    private String contact;
    private  String Iid;
    private String ItemName;
    private double price;
    private int quantity;

    public SupplierItem() {}

    public SupplierItem(String sid, String name, String address, String contact, String iid, String itemName, double price, int quantity) {
        Sid = sid;
        this.name = name;
        this.address = address;
        this.contact = contact;
        Iid = iid;
        ItemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getSid() {
        return Sid;
    }

    public void setSid(String sid) {
        Sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getIid() {
        return Iid;
    }

    public void setIid(String iid) {
        Iid = iid;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "SupplierItem{" +
                "Sid='" + Sid + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", Iid='" + Iid + '\'' +
                ", ItemName='" + ItemName + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
