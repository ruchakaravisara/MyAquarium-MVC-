package lk.ijse.aquarium.to;

import javafx.scene.control.Alert;
import lk.ijse.aquarium.db.DBConnection;
import lk.ijse.aquarium.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDetails {
    private String Oid;

    private String Cid;
    private  String Did;

    private String Iid;
    private String name;
    private double price;
    private int quantity;

    public OrderDetails() {
    }

    public OrderDetails(String oid, String cid, String did, String iid, String name, double price, int quantity) {
        Oid = oid;
        Cid = cid;
        Did = did;
        Iid = iid;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }



    public String getOid() {
        return Oid;
    }

    public void setOid(String oid) {
        Oid = oid;
    }

    public String getCid() {
        return Cid;
    }

    public void setCid(String cid) {
        Cid = cid;
    }

    public String getDid() {
        return Did;
    }

    public void setDid(String did) {
        Did = did;
    }

    public String getIid() {
        return Iid;
    }

    public void setIid(String iid) {
        Iid = iid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "OrderDetails{" +
                "Oid='" + Oid + '\'' +
                ", Cid='" + Cid + '\'' +
                ", Did='" + Did + '\'' +
                ", Iid='" + Iid + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
