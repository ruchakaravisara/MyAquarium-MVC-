package lk.ijse.aquarium.model;

import lk.ijse.aquarium.to.Customer;
import lk.ijse.aquarium.to.CustomerPayment;
import lk.ijse.aquarium.to.SupplierPayment;
import lk.ijse.aquarium.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerPaymentModel {
    public static boolean save(CustomerPayment customerPayment) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO Customerpayment VALUES (?, ?, ?)";
        return CrudUtil.execute(sql, customerPayment.getId(), customerPayment.getValue(), customerPayment.getcId());


    }
    public static boolean updateCustomerPayment(CustomerPayment customerPayment,String id) throws SQLException, ClassNotFoundException {
        String sql = " UPDATE customerpayment SET value = ? , cid = ?  WHERE CPid = ? " ;
        return CrudUtil.execute(sql, customerPayment.getValue(), customerPayment.getcId(),id);
    }

    public static CustomerPayment searchCustomerPayment(String id) throws SQLException, ClassNotFoundException {

        String sql = "SELECT  * FROM Customerpayment WHERE CPid = ?";
        ResultSet result = CrudUtil.execute(sql, id);

        if(result.next()) {
            return new CustomerPayment(
                    result.getString(1),
                    result.getDouble(2),
                    result.getString(3)
            );
        }
        return null;
    }

    public static boolean DeleteCustomerPayment(CustomerPayment customerPayment, String id) throws SQLException, ClassNotFoundException {
        String sql = " DELETE FROM customerpayment  WHERE CPid = ? " ;
        return CrudUtil.execute(sql,id);
    }

    public static ArrayList<CustomerPayment> getCustomerPaymentData() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerPayment> customerpayment = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute("select * from customerpayment");
        while (resultSet.next()){

            customerpayment.add(new CustomerPayment(resultSet.getString(1),
                    resultSet.getDouble(2),
                    resultSet.getString(3)));
        }
        return customerpayment;
    }
}
