package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {


    public Meal save(Meal Meal, int userId);

    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id AND m.user=:user_id")
    public int delete(@Param("id") int id, @Param("user_id")int userId);

    @Query("SELECT m FROM Meal m WHERE m.id=:id AND m.user=:user_id")
    public Optional<Meal> get(@Param("id") int id, @Param("user_id")int userId);

    @Query("SELECT m FROM Meal m WHERE m.user=:user_id")
    public List<Meal> getAllAndSort(@Param("id") int userId, Sort sort);

    public List<Meal> findByDateTimeBetweenAndUserIsAndSort(LocalDateTime startDate, LocalDateTime endDate, int userId, Sort sort);

    //public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId);
}
