package com.wq.shardingsphere.example.dao;

import com.wq.shardingsphere.example.model.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserAccountDao {

    private final Logger logger = LoggerFactory.getLogger(UserAccountDao.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public UserAccount queryAccountById(Integer id){
        String sql = "select id,name,age,email,address from user_account where id = ?";

        UserAccount result = jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper<UserAccount>() {
            @Override
            public UserAccount mapRow(ResultSet resultSet, int i) throws SQLException {
                logger.info("record row:{}",i);
                UserAccount userAccount = new UserAccount();
                userAccount.setId(resultSet.getInt(1));
                userAccount.setName(resultSet.getString(2));
                userAccount.setAge(resultSet.getInt(3));
                userAccount.setEmail(resultSet.getString(4));
                userAccount.setAddress(resultSet.getString(5));
                return userAccount;
            }
        });

        return result;
    }
}
