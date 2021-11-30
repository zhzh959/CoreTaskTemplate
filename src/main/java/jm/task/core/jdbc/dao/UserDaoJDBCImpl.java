package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String INSERT_NEW = "INSERT INTO usersSql VALUES (?,?,?,?)";
    private static final String GET_ALL = "SELECT * FROM usersSql";
    private static final String DELETE_ID = "DELETE FROM usersSql WHERE ID = ?";
    private static final String DELETE = "DELETE FROM usersSql";
    PreparedStatement preparedStatement = null;

    public UserDaoJDBCImpl() {

    }

    Util util = new Util();

    @Override
    public void createUsersTable() {
        String sqlCommand = "CREATE TABLE IF NOT EXISTS usersSql (Id INT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(20), " +
                "LastName VARCHAR(20), Age TINYINT(1))";
        try {
            preparedStatement = util.getConnection().prepareStatement(INSERT_NEW);
            ResultSet rs = preparedStatement.executeQuery("SELECT id, name, lastName, age FROM usersSql");
            if (rs == null) {
                preparedStatement.executeUpdate(sqlCommand);
            }
            System.out.println("Таблица создана!");
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                util.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        try {
            preparedStatement.executeUpdate("DROP TABLE IF EXISTS usersSql");
            System.out.println("Таблица удалена!");
        } catch (SQLException | NullPointerException e) {
            System.out.println("Таблица удалена!");
        } finally {
            try {
                preparedStatement.close();
                util.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            preparedStatement = util.getConnection().prepareStatement(INSERT_NEW);
            int rows = preparedStatement.executeUpdate("INSERT usersSql (name, lastName, age) VALUES ('Alex', 'Popov', 25)," +
                    "('Sergey', 'Petrov', 45), ('Igor', 'Sidorov', 35), ('Ivan', 'Ivanov', 55)");
            ResultSet rs = preparedStatement.executeQuery("SELECT name FROM usersSql");
            while (rs.next()) {
                name = rs.getString(1);
                System.out.println("User с именем – " + name + " добавлен в базу данных");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                util.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        try {
            preparedStatement = util.getConnection().prepareStatement(DELETE_ID);
            preparedStatement.setInt(1, 1);
            preparedStatement.execute();
            System.out.println("Remove 1 rows");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                util.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try {
            preparedStatement = util.getConnection().prepareStatement(INSERT_NEW);
            ResultSet resultSet = preparedStatement.executeQuery("SELECT id, name, lastName, age FROM usersSql");

            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                byte age = resultSet.getByte(4);
                User user = new User(id);
                user.setId((long) id);
                user.setName(name);
                user.setLastName(lastName);
                user.setAge((byte) age);
                result.add(user);
                System.out.println(user);
            }
        } catch (SQLException | NullPointerException e) {
            System.out.println("Ошибка вывода в консоль");
        } finally {
            try {
                preparedStatement.close();
                util.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void cleanUsersTable() {
        try {
            preparedStatement = util.getConnection().prepareStatement(DELETE);
            int rows = preparedStatement.executeUpdate(DELETE);
            System.out.printf("Clean %d rows! \n", rows);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                util.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
