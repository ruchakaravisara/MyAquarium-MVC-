package lk.ijse.aquarium.model;

import lk.ijse.aquarium.to.Customer;
import lk.ijse.aquarium.to.Item;
import lk.ijse.aquarium.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemModel {
    public static boolean saveItem(Item item) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO Item VALUES (?, ?, ?, ?)";
        return CrudUtil.execute(sql, item.getId(), item.getName(), item.getPrice(), item.getQuantity());


    }


    public static boolean updateItem(Item item,String id) throws SQLException, ClassNotFoundException {
        String sql = " UPDATE Item SET name = ? , price = ? , quantity = ? WHERE Iid = ? " ;
        return CrudUtil.execute(sql, item.getName(), item.getPrice(), item.getQuantity(),id);
    }

    public static Item searchItem(String id) throws SQLException, ClassNotFoundException {

        String sql = "SELECT * FROM Item WHERE Iid = ?";
        ResultSet result = CrudUtil.execute(sql, id);

        if(result.next()) {
            return new Item(
                    result.getString(1),
                    result.getString(2),
                    result.getDouble(3),
                    result.getInt(4)
            );
        }
        return null;
    }

    public static boolean DeleteItem(Item item,String id) throws SQLException, ClassNotFoundException {
        String sql = " DELETE FROM item  WHERE Iid = ? " ;
        return CrudUtil.execute(sql,id);
    }
    public static String getCurrentItemID() throws SQLException, ClassNotFoundException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM item ORDER BY CAST(SUBSTRING(Iid, 2) AS UNSIGNED) DESC LIMIT 1");
        while (rs.next()) {
            return rs.getString(1);
        }
        return null;
    }

    public static ArrayList<Item> getItemData() throws SQLException, ClassNotFoundException {
        ArrayList<Item> itemData = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute("select * from item");
        while (resultSet.next()){

            itemData.add(new Item(resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4)));

        }
        return itemData;
    }
}
