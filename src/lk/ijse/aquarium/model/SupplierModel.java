package lk.ijse.aquarium.model;

import lk.ijse.aquarium.to.Customer;
import lk.ijse.aquarium.to.Supplier;
import lk.ijse.aquarium.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierModel {
    public static boolean saveSupplier(Supplier supplier) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO Supplier VALUES (?, ?, ?, ?)";
        return CrudUtil.execute(sql, supplier.getId(), supplier.getName(), supplier.getAddress(), supplier.getContact());


    }


    public static boolean updateSupplier(Supplier supplier,String id) throws SQLException, ClassNotFoundException {
        String sql = " UPDATE supplier SET name = ? , address = ? , contact = ? WHERE Sid = ? " ;
        return CrudUtil.execute(sql, supplier.getName(), supplier.getAddress(), supplier.getContact(),id);
    }

    public static Supplier searchSupplier(String id) throws SQLException, ClassNotFoundException {

        String sql = "SELECT  * FROM Supplier WHERE Sid = ?";
        ResultSet result = CrudUtil.execute(sql, id);

        if(result.next()) {
            return new Supplier(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4)
            );
        }
        return null;
    }

    public static boolean DeleteSupplier(Supplier supplier,String id) throws SQLException, ClassNotFoundException {
        String sql = " DELETE FROM supplier  WHERE Sid = ? " ;
        return CrudUtil.execute(sql,id);
    }
    public static String getCurrentItemID() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM supplier ORDER BY CAST(SUBSTRING(Sid, 2) AS UNSIGNED) DESC LIMIT 1");
        while (rs.next()) {
            return rs.getString(1);
        }
        return null;
    }
    public static ArrayList<Supplier> getSupplierData() throws SQLException, ClassNotFoundException {
        ArrayList<Supplier> supplierData = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute("select * from supplier");
        while (resultSet.next()){

            supplierData.add(new Supplier(resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)));
        }
        return supplierData;
    }
}
