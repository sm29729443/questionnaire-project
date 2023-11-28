package com.example.questionnaireproject.dao.impl;

import com.example.questionnaireproject.dao.QuestionnaireDao;
import com.example.questionnaireproject.dto.QuestionnaireQueryParam;
import com.example.questionnaireproject.model.PrimaryQuestionnaire;
import com.example.questionnaireproject.rowmapper.QuestionnaireRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class QuestionnaireDaoImpl implements QuestionnaireDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<PrimaryQuestionnaire> getPrimaryQuestionnaires(QuestionnaireQueryParam questionnaireQueryParam) {
        // 一開始的查詢條件就包含以 搜尋時間 作為查詢
        String sql = "SELECT pq_id, pq_title, pq_status, pq_created_date, pq_ended_date," +
                " pq_description FROM primary_questionnaire WHERE pq_created_date >= :createdDate" +
                " AND pq_ended_date <= :endedDate";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("createdDate", questionnaireQueryParam.getBeginDate());
        map.put("endedDate", questionnaireQueryParam.getEndedDate());
        // 模糊查詢
        sql = addFilteringSql(sql, map, questionnaireQueryParam);

        // 分頁
        sql = sql + " LIMIT :limit OFFSET :offset";
        map.put("limit", questionnaireQueryParam.getLimit());
        map.put("offset", questionnaireQueryParam.getOffset());
        List<PrimaryQuestionnaire> primaryQuestionnaireList = namedParameterJdbcTemplate.query(sql, map, new QuestionnaireRowMapper());
        return primaryQuestionnaireList;
    }

    @Override
    public Integer countQuestionnaires(QuestionnaireQueryParam questionnaireQueryParam) {
        String sql = " SELECT count(*) FROM primary_questionnaire " +
                "WHERE pq_created_date >= :createdDate AND pq_ended_date <= :endedDate";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("createdDate", questionnaireQueryParam.getBeginDate());
        map.put("endedDate", questionnaireQueryParam.getEndedDate());
        //模糊查詢
        addFilteringSql(sql, map, questionnaireQueryParam);
        return namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
    }

    public String addFilteringSql(String sql, Map<String, Object> map, QuestionnaireQueryParam questionnaireQueryParam) {
        // 判斷是否有 搜尋標題 的模糊查詢
        if (questionnaireQueryParam.getSearchTitle() != null) {
            sql = sql + " AND pq_title LIKE :pqTitle";
            map.put("pqTitle", "%" + questionnaireQueryParam.getSearchTitle() + "%");
        }
        return sql;
    }
}
