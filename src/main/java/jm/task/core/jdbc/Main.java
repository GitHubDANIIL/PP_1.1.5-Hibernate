package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
//        userService.saveUser("Irina", "Grigoriyanko", (byte) 34);
//        userService.saveUser("Marina", "Orlova", (byte) 18);
//        userService.saveUser("Andrey", "Stepanov", (byte) 41);
//        userService.saveUser("Kirill", "Borisov", (byte) 15);
//        userService.removeUserById(2);
//        List<User> users = userService.getAllUsers();
//        for (User user : users) {
//            System.out.println(user);
//        }
//        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
