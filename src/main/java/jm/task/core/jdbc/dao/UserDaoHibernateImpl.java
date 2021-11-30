package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.transaction.Transaction;
import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    org.hibernate.Transaction transaction = null;
    String sql = null;

    Util util = new Util();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Session session = util.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            sql = "CREATE TABLE IF NOT EXISTS usersHiber (Id INT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(20), " +
                    "LastName VARCHAR(20), Age TINYINT(1))";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
            System.out.println("Таблица создана!");

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
            try {
                util.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = util.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            sql = "DROP TABLE IF EXISTS usersHiber";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
            try {
                util.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        List<User> users = null;
        Session session = util.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            sql = "INSERT usersHiber (id, name, lastName, age) VALUES (1, 'Alex', 'Popov', 25),(2, 'Sergey', 'Petrov', 45), " +
                    "(3, 'Igor', 'Sidorov', 35), (4, 'Ivan', 'Ivanov', 55)";
            Query query = session.createSQLQuery(sql);
            query.executeUpdate();

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
            try {
                util.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = util.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            sql = "DELETE FROM usersHiber WHERE id = ?";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
            System.out.println("User удален!");

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
            try {
                util.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = util.getSessionFactory().openSession();
        List<User> users = null;
        try {
            transaction = session.beginTransaction();
            sql = "FROM User";
            Query query = session.createQuery(sql);
            users = query.list();
            transaction.commit();
            System.out.println(users);

        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
            try {
                util.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }


    @Override
    public void cleanUsersTable() {
        Session session = util.getSessionFactory().openSession();
        try {
            transaction = session.beginTransaction();
            sql = "TRUNCATE TABLE usersHiber";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
            try {
                util.getConnection().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}






