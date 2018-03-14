package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    // false if not found or don't have permission
    @Override
    public boolean delete(int id) {
        if (repository.containsKey(id)) {
            if (AuthorizedUser.id() == repository.get(id).getUserId()){
                log.info("delete {}", id);
                repository.remove(id);
                return true;
            } else {
                log.info("Access violation. Unsuccessful delete {} for", id);
                return false;
            }

        } else {
            log.info("Id not found. Unsuccessful delete {} for", id);
            return false;
        }
    }

    // null if not found  or don't have permission
    @Override
    public Meal get(int id) {
        Meal meal = repository.get(id);
        if ((meal!=null) && (AuthorizedUser.id() == meal.getUserId())){
            return meal;
        }
        return null;
    }

    @Override
    public List<Meal> getAll() {
        List<Meal> result;
        result = repository.values().stream().filter((meal -> (meal.getUserId() == AuthorizedUser.id())))
                .sorted(Comparator.comparing(Meal::getDateTime))
                .collect(Collectors.toList());
        return result;
    }

    @Override
    public List<Meal> getByDateTimePeriod(LocalDateTime startDateTime, LocalDateTime endDateTime){
        List<Meal> result;
        result = repository.values().stream().filter((meal -> (meal.getUserId() == AuthorizedUser.id())))
                .filter((meal -> (DateTimeUtil.isBetween(meal.getDateTime(), startDateTime, endDateTime))))
                .sorted(Comparator.comparing(Meal::getDateTime))
                .collect(Collectors.toList());
        return result;
    }
}

