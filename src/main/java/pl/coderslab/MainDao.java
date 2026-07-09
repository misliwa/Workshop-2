package pl.coderslab;

import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

public class MainDao {
    static void main() {
        DbCredentials.readDatabaseCredentials();

        User user = new User();
        user.setUserName("uzytkownik");
        user.setEmail("user@usermail.com");
        user.setPassword("megaTrudneHaslo");

        UserDao userDao = new UserDao();
        //user = userDao.create(user);

        User userFromDb = userDao.read(1);

        System.out.println(userFromDb);
    }
}
