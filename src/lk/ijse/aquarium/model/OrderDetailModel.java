package lk.ijse.aquarium.model;

import javafx.scene.control.Alert;
import lk.ijse.aquarium.db.DBConnection;
import lk.ijse.aquarium.to.OrderDetails;
import lk.ijse.aquarium.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailModel {


    public static ArrayList<OrderDetails> getItemsData() throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetails> otherStockItemData = new ArrayList<>();

        ResultSet rs = CrudUtil.execute("select orderDetails.Oid,\n" +
                "       Cid,\n" +
                "       Did,\n" +
                "       orderDetails.Iid,\n" +
                "       name,\n" +
                "       price,\n" +
                "       quantity\n" +
                "from orderDetails\n" +
                "         join orders o on o.Oid =orderDetails.Oid\n" +
                "         join Item i on i.Iid=orderDetails.Iid ;");
        while (rs.next()) {
            otherStockItemData.add(new OrderDetails(rs.getString("Oid"),

                    rs.getString("Cid"),
                    rs.getString("Did"),
                    rs.getString("Iid"),

                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("quantity")));
        }
        return otherStockItemData;
    }
    public static String getCurrentItemID() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM orders ORDER BY CAST(SUBSTRING(Oid, 2) AS UNSIGNED) DESC LIMIT 1");
        while (rs.next()) {
            return rs.getString(1);
        }
        return null;
    }

    public static boolean insertNewItemTransaction(OrderDetails orderDetails) throws SQLException, ClassNotFoundException {
        String oid = orderDetails.getOid();
        String cid = orderDetails.getCid();
        String did = orderDetails.getDid();
        String iid = orderDetails.getIid();
        String name = orderDetails.getName();
        double price = orderDetails.getPrice();
        int quantity = orderDetails.getQuantity();
        String date = "2019-03-03";

        Connection connection = DBConnection.getDbConnection().getConnection();

        connection.setAutoCommit(false);

        Boolean isInsertedToorders = CrudUtil.execute("insert into orders (Oid, Cid, Did)\n" +
                "values (?,?,?);", oid, cid, did);
        Boolean isInsertedToorderDetails = CrudUtil.execute("insert into orderDetails (Oid, Iid, Date)\n" +
                "values (?,?,?)", oid, iid, date);

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
    public static boolean updateOrderItem(OrderDetails orderDetails) throws SQLException, ClassNotFoundException {
        String oid = orderDetails.getOid();
        String cid = orderDetails.getCid();
        String did = orderDetails.getDid();
        String iid = orderDetails.getIid();
        String name = orderDetails.getName();
        double price = orderDetails.getPrice();
        int quantity = orderDetails.getQuantity();
        String date = "2019-03-03";

        Connection connection = DBConnection.getDbConnection().getConnection();

        connection.setAutoCommit(false);

        Boolean isInsertedToOrders = CrudUtil.execute("update orders\n" +
                "set Cid = ?,Did = ? \n" +
                "where Oid = ? ",cid,did,oid);

        Boolean isInsertedToOrderDetails = CrudUtil.execute("update orderDetails\n" +
                "set Iid = ?,date =? \n" +
                "where Oid = ? ",iid,date,oid);

        if (isInsertedToOrderDetails && isInsertedToOrders) {
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

    public static Boolean deleteOrderDetailTransactions(String Oid) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();

        connection.setAutoCommit(false);

        Boolean isDeletedFromOrders = CrudUtil.execute("delete from orders where Oid = ? " ,Oid);
        Boolean isDeletedFromOrderDetails = CrudUtil.execute("delete from orderDetails where Oid = ? " ,Oid);

        if (isDeletedFromOrderDetails && isDeletedFromOrders) {
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
    ////////////////////////////
    public static ArrayList<String> loadItemCodes() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT Iid FROM item");
        ArrayList<String> codes = new ArrayList<>();

        while(result.next()) {
            codes.add(result.getString(1));
        }
        return codes;
    }
    public static ResultSet getitemmdetails(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("SELECT * FROM item WHERE Iid=?",id);
    }
}
