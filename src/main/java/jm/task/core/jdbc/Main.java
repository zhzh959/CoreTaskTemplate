package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SessionFactory;

import java.sql.*;

public class Main {

    public static void main(String[] args) {
        Util util = new Util();
        UserServiceImpl userService = new UserServiceImpl();

        while (true) {

                userService.createUsersTable();
                userService.saveUser("Sasha", "Filippov", (byte) 14);
                userService.getAllUsers();
                userService.cleanUsersTable();
                userService.dropUsersTable();

            break;
        }
    }
}
