package com.example.questionnaireproject.rowmapper;

import com.example.questionnaireproject.model.UserInfoOne;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ClassName: UserInfoOneRowMapper
 * Package: com.example.questionnaireproject.rowmapper
 */
public class UserInfoOneRowMapper implements RowMapper<UserInfoOne> {
    @Override
    public UserInfoOne mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserInfoOne userInfoOne = new UserInfoOne();
        userInfoOne.setUserId(rs.getInt("user_id"));
        userInfoOne.setUserName(rs.getString("user_name"));
        userInfoOne.setPqId(rs.getInt("pq_id"));
        userInfoOne.setCreatedDate(rs.getDate("created_time"));
        return userInfoOne;
    }
}
