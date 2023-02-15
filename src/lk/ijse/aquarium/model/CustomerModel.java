package lk.ijse.aquarium.model;

import lk.ijse.aquarium.to.Customer;
import lk.ijse.aquarium.to.Orders;
import lk.ijse.aquarium.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {
    public static boolean save(Customer customer) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO Customer VALUES (?, ?, ?, ?)";
        return CrudUtil.execute(sql, customer.getId(), customer.getName(), customer.getAddress(), customer.getContact());


    }


   public static boolean updateCustomer(Customer customer,String id) throws SQLException, ClassNotFoundException {
       String sql = " UPDATE customer SET name = ? , address = ? , contact = ? WHERE Cid = ? " ;
       return CrudUtil.execute(sql, customer.getName(), customer.getAddress(), customer.getContact(),id);
   }

    public static Customer searchCustomer(String id) throws SQLException, ClassNotFoundException {

        String sql = "SELECT  * FROM Customer WHERE Cid = ?";
        ResultSet result = CrudUtil.execute(sql, id);

        if(result.next()) {
            return new Customer(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4)
            );
        }
        return null;
    }

    public static boolean DeleteCustomer(Customer customer, String id) throws SQLException, ClassNotFoundException {
        String sql = " DELETE FROM customer  WHERE Cid = ? " ;
        return CrudUtil.execute(sql,id);
    }

    public static String getCurrentItemID() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM customer ORDER BY CAST(SUBSTRING(Cid, 2) AS UNSIGNED) DESC LIMIT 1");
        while (rs.next()) {
            return rs.getString(1);
        }
        return null;
    }

    public static ArrayList<Customer> getCustomerData() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customerData = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute("select * from customer");
        while (resultSet.next()){

            customerData.add(new Customer(resultSet.getString(1),
            resultSet.getString(2),
            resultSet.getString(3),
            resultSet.getString(4)));
        }
        return customerData;
    }

}
