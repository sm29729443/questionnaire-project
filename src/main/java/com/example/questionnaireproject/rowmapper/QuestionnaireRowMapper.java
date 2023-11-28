package com.example.questionnaireproject.rowmapper;

import com.example.questionnaireproject.constants.QuestionnaireStatus;
import com.example.questionnaireproject.model.PrimaryQuestionnaire;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ClassName: QuestionnaireRowMapper
 * Package: com.example.questionnaireproject.rowmapper
 */
public class QuestionnaireRowMapper implements RowMapper<PrimaryQuestionnaire> {
    @Override
    public PrimaryQuestionnaire mapRow(ResultSet rs, int rowNum) throws SQLException {
        PrimaryQuestionnaire primaryQuestionnaire = new PrimaryQuestionnaire();
        primaryQuestionnaire.setPqId(rs.getInt("pq_id"));
        primaryQuestionnaire.setPqTitle(rs.getString("pq_title"));
        primaryQuestionnaire.setPqStatus(QuestionnaireStatus.valueOf(rs.getString("pq_status")));
        primaryQuestionnaire.setPqCreatedDate(rs.getDate("pq_created_date"));
        primaryQuestionnaire.setPqEndedDate(rs.getDate("pq_ended_date"));
        primaryQuestionnaire.setPqDescription(rs.getString("pq_description"));
        return primaryQuestionnaire;
    }
}
