package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface MealRepository {
    Meal save(Meal meal);

    boolean delete(int id);

    Meal get(int id);

    List<Meal> getAll();

    List<Meal> getByDateTimePeriod(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
