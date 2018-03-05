package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.getMeals;
import static ru.javawebinar.topjava.util.MealsUtil.getMealsWithExeeded;

public class MealServlet extends HttpServlet {

    static final Logger log = LoggerFactory.getLogger(MealServlet.class);

/*
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
*/

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("get meals list");
        List<MealWithExceed> mealWithExceedList = getMealsWithExeeded(getMeals(), MealsUtil.CALORIES_FOR_EXCEEDED);
        request.setAttribute("mealWithExceedList", mealWithExceedList);
        log.debug("dispath request to meals");
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
