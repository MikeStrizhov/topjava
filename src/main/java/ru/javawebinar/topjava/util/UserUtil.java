package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UserUtil {
    public static final List<User> USERS = Arrays.asList(
            new User(1, "user1", "user1@mail.com", "passwd", Role.ROLE_USER),
            new User(2, "user2", "user2@mail.com", "passwd", Role.ROLE_USER),
            new User(3, "user3", "user3@mail.com", "passwd", Role.ROLE_ADMIN),
            new User(4, "user4", "user4@mail.com", "passwd", Role.ROLE_ADMIN, Role.ROLE_USER)
    );

}
