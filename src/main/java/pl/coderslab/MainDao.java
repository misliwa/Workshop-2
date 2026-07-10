package pl.coderslab;

import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

public class MainDao {
    static void main() {
        DbCredentials.readDatabaseCredentials();

        UserDao userDao = new UserDao();

        User[] users = userDao.findAll();

        for(User user : users){
            System.out.println(user);
        }
    }
}
