package lk.ijse.aquarium.model;

import lk.ijse.aquarium.to.Customer;
import lk.ijse.aquarium.to.SupplierPayment;
import lk.ijse.aquarium.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierPaymentModel {
    public static boolean save(SupplierPayment supplierPayment) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO supplierpayment VALUES (?, ?, ?)";
        return CrudUtil.execute(sql, supplierPayment.getId(), supplierPayment.getValue(), supplierPayment.getSid());


    }


    public static boolean updateSupplierPayment(SupplierPayment supplierPayment,String id) throws SQLException, ClassNotFoundException {
        String sql = " UPDATE supplierpayment SET value = ? , sid = ? WHERE SPid = ? " ;
        return CrudUtil.execute(sql, supplierPayment.getValue(), supplierPayment.getSid(),id);
    }

    public static SupplierPayment searchSupplierPayment(String id) throws SQLException, ClassNotFoundException {

        String sql = "SELECT  * FROM supplierpayment WHERE SPid = ?";
        ResultSet result = CrudUtil.execute(sql, id);

        if(result.next()) {
            return new SupplierPayment(
                    result.getString(1),
                    result.getDouble(2),
                    result.getString(3)
            );
        }
        return null;
    }

    public static boolean DeleteSupplierPayment(SupplierPayment supplierPayment, String id) throws SQLException, ClassNotFoundException {
        String sql = " DELETE FROM supplierpayment  WHERE SPid = ? " ;
        return CrudUtil.execute(sql,id);
    }

    public static ArrayList<SupplierPayment> getSupplierPaymentData() throws SQLException, ClassNotFoundException {
        ArrayList<SupplierPayment> supplierpayment = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute("select * from supplierpayment");
        while (resultSet.next()){

            supplierpayment.add(new SupplierPayment(resultSet.getString(1),
                    resultSet.getDouble(2),
                    resultSet.getString(3)));
        }
        return supplierpayment;
    }

}
