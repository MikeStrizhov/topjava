package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = false)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

/*    @Modifying
    @Query("INSERT INTO Meal m WHERE m.id=:id AND m.user=:user_id")
    public Meal insert(Meal meal, int userId);
    public Meal update(Meal meal, int userId);*/
    //public Meal save(Meal Meal, int userId);
    @Override
    @Modifying
    @Transactional
    public Meal save(Meal Meal);

    @Modifying
    @Transactional
    @Query("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:user_id")
    public int delete(@Param("id") int id, @Param("user_id")int userId);

    //SELECT c FROM Cat c left join fetch c.kittens WHERE c.id = :id
    @Query("SELECT m FROM Meal m WHERE m.id=:id AND m.user.id=:user_id")
    public Optional<Meal> get(@Param("id") int id, @Param("user_id")int userId);

    @Query("SELECT m FROM Meal m WHERE m.user.id=:user_id")
    public List<Meal> getAllAndSort(@Param("user_id") int userId, Sort sort);

    public List<Meal> findByDateTimeBetweenAndUserIs(LocalDateTime startDate, LocalDateTime endDate, User user, Sort sort);
    //public List<Meal> findByDateTimeBetweenAndUserIsAndSort(LocalDateTime startDate, LocalDateTime endDate, int userId, Sort sort);

    //public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId);
}
