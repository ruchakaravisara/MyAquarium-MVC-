package lk.ijse.aquarium.model;

import javafx.scene.control.Alert;
import lk.ijse.aquarium.db.DBConnection;
import lk.ijse.aquarium.to.OrderDetails;
import lk.ijse.aquarium.to.SupplierItem;
import lk.ijse.aquarium.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierItemModel {
    public static ArrayList<SupplierItem> getItemsData() throws SQLException, ClassNotFoundException {
        ArrayList<SupplierItem> otherStockItemData = new ArrayList<>();

        ResultSet rs = CrudUtil.execute("select supplyingItems.Sid,\n" +
                "        s.name,\n" +
                "       address,\n" +
                "       contact,\n" +
                "       supplyingItems.Iid,\n" +
                "        i.name,\n" +
                "       price,\n" +
                "       quantity\n" +
                "from supplyingItems\n" +
                "         join supplier s on s.Sid =supplyingItems.Sid\n" +
                "         join Item i on i.Iid=supplyingItems.Iid ;");
        while (rs.next()) {
            otherStockItemData.add(new SupplierItem(rs.getString("Sid"),

                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("contact"),
                    rs.getString("Iid"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("quantity")));
        }
        return otherStockItemData;
    }
   public static String getCurrentItemID() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM supplier ORDER BY CAST(SUBSTRING(Sid, 2) AS UNSIGNED) DESC LIMIT 1");
        while (rs.next()) {
            return rs.getString(1);
        }
        return null;
    }
    public static boolean insertSupplierTransaction(SupplierItem supplierItem) throws SQLException, ClassNotFoundException {
        String Sid = supplierItem.getSid();
        String name = supplierItem.getName();
        String address = supplierItem.getAddress();
        String contact = supplierItem.getContact();
        String Iid = supplierItem.getIid();
        String ItemName = supplierItem.getName();
        double price = supplierItem.getPrice();
        int quantity = supplierItem.getQuantity();


        Connection connection = DBConnection.getDbConnection().getConnection();

        connection.setAutoCommit(false);

        Boolean isInsertedToorders = CrudUtil.execute("insert into supplier (Sid, name, address,contact)\n" +
                "values (?,?,?,?);", Sid, name, address,contact);
        Boolean isInsertedToorderDetails = CrudUtil.execute("insert into supplyingItems (Sid, Iid)\n" +
                "values (?,?)", Sid,Iid);
        /*Boolean isInsertedItemsDetails = CrudUtil.execute("insert into item (Iid, name,price,quantity)\n" +
                "values (?,?,?,?)", Iid,name,price,quantity);*/

        if (isInsertedToorders && isInsertedToorderDetails) {
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } else {
            new Alert(Alert.AlertType.ERROR, "rollback !").show();
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }
    }
   /*public static ArrayList<String> loadSupplierCodes() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT Sid FROM supplier");
        ArrayList<String> codes = new ArrayList<>();

        while(result.next()) {
            codes.add(result.getString(1));
        }
        return codes;
    }
    public static ResultSet getSuppdetailss(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("SELECT * FROM supplier WHERE Sid=?",id);
    }*/
    public static ResultSet getitemmdetailss(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("SELECT * FROM item WHERE Iid=?",id);
    }
    public static ArrayList<String> loadIteCodes() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT Iid FROM item");
        ArrayList<String> codes = new ArrayList<>();

        while(result.next()) {
            codes.add(result.getString(1));
        }
        return codes;
    }
}
