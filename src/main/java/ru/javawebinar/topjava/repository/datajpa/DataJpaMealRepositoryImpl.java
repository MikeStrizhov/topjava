package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {

    private static final Sort SORT_DATETIME_DESC = new Sort(Sort.Direction.DESC, "dateTime");

    @Autowired
    private CrudMealRepository crudRepository;

    @Autowired
    private CrudUserRepository crudUserRepository;

    @Override
    public Meal save(Meal meal, int userId) {
/*
        if (meal.isNew()) {
            crudRepository.insert(meal, userId);
        } else {
            crudRepository.update(meal, userId);
        }
*/

/*        if (meal.isNew()) {
            meal.setUser(crudUserRepository.findById(userId).get());
            crudRepository.insert(meal, userId);
        } else {
            crudRepository.update(meal, userId);
        }*/

        meal.setUser(crudUserRepository.findById(userId).get());
        return crudRepository.save(meal);

        //return crudRepository.save(Meal, userId);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.delete(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
       return crudRepository.get(id, userId).orElse(null);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.getAllAndSort(userId, SORT_DATETIME_DESC);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        User user = crudUserRepository.findById(userId).get();
        return crudRepository.findByDateTimeBetweenAndUserIs(startDate, endDate, user, SORT_DATETIME_DESC);
    }
}
