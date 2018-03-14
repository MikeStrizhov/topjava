package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundCollection;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal create(Meal meal) {
        return repository.save(meal);
    }

    @Override
    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public Meal get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    @Override
    public List<Meal> getByDateTimePeriod(LocalDateTime startDateTime, LocalDateTime endDateTime) throws NotFoundException{
        String msg = "start date time=" + startDateTime.toString() + " end date time=" + endDateTime.toString();
        return checkNotFoundCollection(repository.getByDateTimePeriod(startDateTime, endDateTime), msg);
    }

    @Override
    public List<Meal> getAll() {
        return repository.getAll();
    }

    @Override
    public void update(Meal meal) {
        checkNotFoundWithId(repository.save(meal), meal.getId());
    }
}