package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.UserUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {

    private final Integer INITIAL_VALUE = 10000;

    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(INITIAL_VALUE);

    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);


    {
        UserUtil.USERS.forEach(this::save);
    }

    @Override
    public boolean delete(int id) {
        if (repository.containsKey(id)) {
            log.info("delete {}", id);
            repository.remove(id);
            return true;
        } else {
            log.info("Unsuccessful delete {} for", id);
            return false;
        }
    }

    @Override
    public User save(User user) {
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            log.info("save new user {}", user);
            return user;
        }
        log.info("save (update) user {}", user);
        // treat case: update, but absent in storage
        return repository.computeIfPresent(user.getId(), (id, oldMeal) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        List<User> result = new ArrayList<>(repository.values());
        result.sort(Comparator.comparing(AbstractNamedEntity::getName));
        //оставлю второй вариант сортировки
        //Collections.sort(result, (a,b)->a.getName().compareTo(b.getName()));
        //Collections.sort(result, Comparator.comparing(AbstractNamedEntity::getName));
        return result;
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);

        for (Map.Entry<Integer, User> entry:repository.entrySet()){
            if (email.equalsIgnoreCase(entry.getValue().getEmail())){
                return entry.getValue();
            }
        }
        return null;
    }

/*
    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return true;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        return user;
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return null;
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return Collections.emptyList();
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return null;
    }
*/
}
