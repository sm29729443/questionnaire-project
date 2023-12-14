package com.example.questionnaireproject.rowmapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ClassName: QuestionnaireRowMapperForStatus
 * Package: com.example.questionnaireproject.rowmapper
 */
public class QuestionnaireRowMapperForStatus implements RowMapper<String> {
    @Override
    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getString("pq_status");
    }
}
