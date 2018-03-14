package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
/*
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email", "password", Role.ROLE_ADMIN));
        }
*/
        checkMeal();
    }

    public  static void checkUser(){
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email", "password", Role.ROLE_ADMIN));
        }
    }

    public  static void checkMeal() {
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            MealRestController mealController = appCtx.getBean(MealRestController.class);
            //mealController.create(new User(null, "userName", "email", "password", Role.ROLE_ADMIN));
            mealController.create( new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510, 1));
            mealController.getAll().forEach(meal -> System.out.println(meal.toString()));
        }
    }
}
