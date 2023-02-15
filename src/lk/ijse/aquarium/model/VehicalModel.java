package lk.ijse.aquarium.model;

import lk.ijse.aquarium.to.Customer;
import lk.ijse.aquarium.to.Vehical;
import lk.ijse.aquarium.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VehicalModel {
    public static boolean saveVehical(Vehical vehical) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO Vehical VALUES (?, ?, ?)";
        return CrudUtil.execute(sql, vehical.getId(), vehical.getName(), vehical.getEmpid());


    }


    public static boolean updateVehical(Vehical vehical,String id) throws SQLException, ClassNotFoundException {
        String sql = " update Vehical set name = ? , empid = ? , WHERE Vid = ?" ;
        return CrudUtil.execute(sql, vehical.getName(), vehical.getEmpid(),id);
    }

    public static Vehical searchVehical(String id) throws SQLException, ClassNotFoundException {

        String sql = "SELECT  * FROM Vehical WHERE Vid = ?";
        ResultSet result = CrudUtil.execute(sql, id);

        if(result.next()) {
            return new Vehical(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3)

            );
        }
        return null;
    }

    public static boolean DeleteVehical(Vehical vehical,String id) throws SQLException, ClassNotFoundException {
        String sql = " DELETE FROM Vehical  WHERE Vid = ? " ;
        return CrudUtil.execute(sql,id);
    }
    public static String getCurrentItemID() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM vehical ORDER BY CAST(SUBSTRING(Vid, 2) AS UNSIGNED) DESC LIMIT 1");
        while (rs.next()) {
            return rs.getString(1);
        }
        return null;
    }

    public static ArrayList<Vehical> getVehicalData() throws SQLException, ClassNotFoundException {
        ArrayList<Vehical> vehicalData = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute("select * from vehical");
        while (resultSet.next()){

            vehicalData.add(new Vehical(resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3)));
        }
        return vehicalData;
    }
}
