package com.example.questionnaireproject.rowmapper;

import com.example.questionnaireproject.constants.QuestionType;
import com.example.questionnaireproject.model.Question;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * ClassName: QuestionRowMapper
 * Package: com.example.questionnaireproject.rowmapper
 */
public class QuestionRowMapper implements RowMapper<Question> {
    @Override
    public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
        Question question = new Question();
        question.setPqId(rs.getInt("pq_id"));
        question.setQId(rs.getInt("q_id"));
        question.setQProblem(rs.getString("q_problem"));
        question.setQAnswer(rs.getString("q_answer"));
        question.setRequireSelect(rs.getBoolean("require_select"));
        question.setProblemType(QuestionType.valueOf(rs.getString("problem_type")));
        return question;
    }
}
