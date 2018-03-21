package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {

    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final DataSource dataSource;

    private final SimpleJdbcInsert insertMeal;

    @Autowired
    public JdbcMealRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.dataSource = dataSource;
        this.insertMeal = new SimpleJdbcInsert(dataSource).
                withTableName("meals").usingGeneratedKeyColumns("id");
    }


    @Override
    public Meal save(Meal meal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("user_id", userId)
                .addValue("datetime", Timestamp.valueOf(meal.getDateTime()))
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories());
        if (meal.isNew()){
            Number newKey = insertMeal.executeAndReturnKey(map);
            meal.setId(newKey.intValue());
        }  else {
            int result = namedParameterJdbcTemplate.update("UPDATE meals SET user_id=:user_id, " +
                    "datetime=:datetime, description=:description, calories=:calories WHERE id=:id", map);
            if (result == 0){
                return null;
            }
        }

        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id=? AND user_id=?", id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> users = jdbcTemplate.query("SELECT * FROM meals WHERE id=? AND user_id=?", ROW_MAPPER, id, userId);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE user_id=? ORDER BY datetime DESC", ROW_MAPPER, userId);

    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        Timestamp timestampStart = Timestamp.valueOf(startDate);
        Timestamp timestampEnd = Timestamp.valueOf(endDate);
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("start_date", timestampStart)
                .addValue("end_date",timestampEnd)
                .addValue("user_id", userId);

/*        return namedParameterJdbcTemplate.queryForList("select id from meals where user_id=:user_id " +
                        "AND datetime BETWEEN :start_date AND :end_date ORDER BY datetime DESC",
                namedParameters, Meal.class);*/
        /*List<Meal> result= namedParameterJdbcTemplate.queryForList("select * from meals where user_id=:user_id " +
                        "AND datetime BETWEEN :start_date AND :end_date ORDER BY datetime DESC",
                namedParameters, Meal.class);*/

        List<Meal> result= namedParameterJdbcTemplate.query("select * from meals where user_id=:user_id " +
                        "AND datetime BETWEEN :start_date AND :end_date ORDER BY datetime DESC",
                namedParameters, ROW_MAPPER);

        //namedParameterJdbcTemplate.
        return result;
    }
}
