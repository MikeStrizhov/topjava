package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

public interface MealService {

    Meal create(Meal user);

    void delete(int id) throws NotFoundException;

    Meal get(int id) throws NotFoundException;

    void update(Meal user);

    List<Meal> getAll();

    List<Meal> getByDateTimePeriod(LocalDateTime startDateTime, LocalDateTime endDateTime) throws NotFoundException;

}