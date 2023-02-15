package lk.ijse.aquarium.model;

import lk.ijse.aquarium.to.Customer;
import lk.ijse.aquarium.to.Orders;
import lk.ijse.aquarium.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrdersModel {
    public static boolean saveOrders(Orders orders) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO Orders VALUES (?, ?, ?)";
        return CrudUtil.execute(sql, orders.getId(), orders.getCid(), orders.getDid());


    }


    public static boolean updateOrders(Orders orders,String id) throws SQLException, ClassNotFoundException {
        String sql = " UPDATE orders SET cid = ? , did = ? WHERE Oid = ? " ;
        return CrudUtil.execute(sql, orders.getCid(), orders.getDid(),id);
    }

    public static Orders searchOrders(String id) throws SQLException, ClassNotFoundException {

        String sql = "SELECT  * FROM Orders WHERE Oid = ?";
        ResultSet result = CrudUtil.execute(sql, id);

        if(result.next()) {
            return new Orders(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3)

            );
        }
        return null;
    }

    public static boolean DeleteOrders(Orders orders,String id) throws SQLException, ClassNotFoundException {
        String sql = " DELETE FROM Orders  WHERE Oid = ? " ;
        return CrudUtil.execute(sql,id);
    }
    public static String getCurrentItemID() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM orders ORDER BY CAST(SUBSTRING(Oid, 2) AS UNSIGNED) DESC LIMIT 1");
        while (rs.next()) {
            return rs.getString(1);
        }
        return null;
    }

    public static ArrayList<Orders> getOrderData() throws SQLException, ClassNotFoundException {
        ArrayList<Orders> orderData = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute("select * from orders");
        while (resultSet.next()){

            orderData.add(new Orders(resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)));
        }
        return orderData;
    }

}
