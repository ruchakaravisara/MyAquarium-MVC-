package lk.ijse.aquarium.model;

import lk.ijse.aquarium.to.Customer;
import lk.ijse.aquarium.to.Employee;
import lk.ijse.aquarium.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeModel {
    public static boolean saveEmployee(Employee employee) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO Employee VALUES (?, ?, ?, ?)";
        return CrudUtil.execute(sql, employee.getId(), employee.getName(), employee.getAddress(), employee.getContact());


    }


    public static boolean updateEmployee(Employee employee,String id) throws SQLException, ClassNotFoundException {
        String sql = " UPDATE employee SET name = ? , address = ? , contact = ? WHERE Eid = ? " ;
        return CrudUtil.execute(sql, employee.getName(), employee.getAddress(), employee.getContact(),id);
    }

    public static Employee searchEmployee(String id) throws SQLException, ClassNotFoundException {

        String sql = "SELECT  * FROM Employee WHERE Eid = ?";
        ResultSet result = CrudUtil.execute(sql, id);

        if(result.next()) {
            return new Employee(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4)
            );
        }
        return null;
    }

    public static boolean DeleteEmployee(Employee employee,String id) throws SQLException, ClassNotFoundException {
        String sql = " DELETE FROM employee  WHERE Eid = ? " ;
        return CrudUtil.execute(sql,id);
    }
    public static String getCurrentItemID() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM employee ORDER BY CAST(SUBSTRING(Eid, 2) AS UNSIGNED) DESC LIMIT 1");
        while (rs.next()) {
            return rs.getString(1);
        }
        return null;
    }

    public static ArrayList<Employee> getEmployeeData() throws SQLException, ClassNotFoundException {
        ArrayList<Employee> employeeData = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute("select * from employee");
        while (resultSet.next()){

            employeeData.add(new Employee(resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)));
        }
        return employeeData;
    }
}
