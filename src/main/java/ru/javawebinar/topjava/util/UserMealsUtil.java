package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        List<UserMealWithExceed> list = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
        list.stream().forEach(userMealWithExceed -> System.out.println(userMealWithExceed.toString()));
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMeal> filteredMeals = new ArrayList<>();
        Map<LocalDate, Integer> caloriesData = new HashMap<>();
        //за один проход фильтруем по времени приема пищи
        for (UserMeal meal: mealList){
            //собираем данные о потребленных за день калориях
            updateCaloriesData(caloriesData, meal);
            LocalTime mealTime = meal.getDateTime().toLocalTime();
            if (mealTime.isAfter(startTime) && mealTime.isBefore(endTime)){
                filteredMeals.add(meal);
            }
        }

        List<UserMealWithExceed> mealWithExceedList = new ArrayList<>();
        for (UserMeal meal: filteredMeals) {
            int calories = caloriesData.get(meal.getDateTime().toLocalDate());
            boolean isExceed = calories > caloriesPerDay;
            mealWithExceedList.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), calories, isExceed));

        }
        return mealWithExceedList;
    }

    private static void updateCaloriesData(Map<LocalDate, Integer> caloriesData, UserMeal meal){
        LocalDate date = meal.getDateTime().toLocalDate();
        if (caloriesData.containsKey(date)){
            Integer newValue = caloriesData.get(date) + meal.getCalories();
            caloriesData.replace(date, newValue);
        } else {
            caloriesData.put(date, meal.getCalories());
        }
    }
}
