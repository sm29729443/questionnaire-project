package com.example.questionnaireproject.dao.impl;

import com.example.questionnaireproject.dao.QuestionnaireDao;
import com.example.questionnaireproject.dto.QuestionRequest;
import com.example.questionnaireproject.dto.QuestionnaireQueryParam;
import com.example.questionnaireproject.dto.UserInfoRequest;
import com.example.questionnaireproject.model.PrimaryQuestionnaire;
import com.example.questionnaireproject.model.Question;
import com.example.questionnaireproject.model.UserInfoOne;
import com.example.questionnaireproject.rowmapper.QuestionRowMapper;
import com.example.questionnaireproject.rowmapper.QuestionnaireRowMapper;
import com.example.questionnaireproject.rowmapper.UserInfoOneRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
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
                "WHERE pq_created_date >= :createdDate AND pq_ended_date <= :endedDate ";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("createdDate", questionnaireQueryParam.getBeginDate());
        map.put("endedDate", questionnaireQueryParam.getEndedDate());
        //模糊查詢
        addFilteringSql(sql, map, questionnaireQueryParam);
        return namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
    }

    @Override
    public Integer createPrimaryQuestionnaire(PrimaryQuestionnaire primaryQuestionnaire) {
        String sql = "INSERT INTO primary_questionnaire(pq_title, pq_status, pq_created_date, pq_ended_date, pq_description)" +
                " VALUES (:pqTitle, :pqStatus, :pqCreatedDate, :pqEndedDate, :pqDescription)";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pqTitle", primaryQuestionnaire.getPqTitle());
        map.put("pqStatus", primaryQuestionnaire.getPqStatus().toString());
        map.put("pqCreatedDate", primaryQuestionnaire.getPqCreatedDate());
        map.put("pqEndedDate", primaryQuestionnaire.getPqEndedDate());
        map.put("pqDescription", primaryQuestionnaire.getPqDescription());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public void createQuestion(Integer pqId, List<QuestionRequest> list) {
        String sql = "INSERT INTO question (pq_id, q_problem, q_answer, require_select, problem_type)\n" +
                "VALUES (:pqId, :qProblem, :qAnswer, :requireSelect, :problemType)";
        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[list.size()];
        for (int i = 0; i < list.size(); i++) {
            QuestionRequest questionRequest = list.get(i);
            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("pqId", pqId);
            parameterSources[i].addValue("qProblem", questionRequest.getqProblem());
            parameterSources[i].addValue("qAnswer", questionRequest.getqAnswer());
            parameterSources[i].addValue("requireSelect", questionRequest.getqRequireSelect());
            parameterSources[i].addValue("problemType", questionRequest.getqProblemType().toString());
        }
        namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);
    }

    @Override
    public List<Question> getQuestionsByPqId(Integer pqId) {
        String sql = "SELECT q_id, pq_id, q_problem, q_answer, require_select, problem_type " +
                "FROM question WHERE pq_id = :pqId";
        Map<String, Object> map = new HashMap<>();
        map.put("pqId", pqId);
        List<Question> questionList = namedParameterJdbcTemplate.query(sql, map, new QuestionRowMapper());
        return questionList;
    }

    @Override
    public PrimaryQuestionnaire updatePrimaryQuestionnare(Integer pqId, PrimaryQuestionnaire primaryQuestionnaire) {
        String sql = "UPDATE primary_questionnaire SET pq_title = :pqTitle, pq_status = :pqStatus, " +
                " pq_created_date = :pqCreatedDate, pq_ended_date = :pqEndedDate, pq_description = :pqDescription " +
                " WHERE pq_id = :pqId ";
        Map<String, Object> map = new HashMap<>();
        map.put("pqId", primaryQuestionnaire.getPqId());
        map.put("pqTitle", primaryQuestionnaire.getPqTitle());
        map.put("pqStatus", primaryQuestionnaire.getPqStatus().toString());
        map.put("pqCreatedDate", primaryQuestionnaire.getPqCreatedDate());
        map.put("pqEndedDate", primaryQuestionnaire.getPqEndedDate());
        map.put("pqDescription", primaryQuestionnaire.getPqDescription());
        namedParameterJdbcTemplate.update(sql, map);
        return null;
    }

    @Override
    public void updateQuestion(Integer pqId, List<Question> updateQuestions) {
        String sql = "UPDATE question SET q_problem = :qProblem, q_answer = :qAnswer, require_select = :requireSelect," +
                "  problem_type = :problemType WHERE q_id = :qId";
        Map<String, Object> map = new HashMap<>();
        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[updateQuestions.size()];
        for (int i = 0; i < updateQuestions.size(); i++) {
            Question question = updateQuestions.get(i);
            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("qId", question.getQId());
            parameterSources[i].addValue("qProblem", question.getQProblem());
            parameterSources[i].addValue("qAnswer", question.getQAnswer());
            parameterSources[i].addValue("requireSelect", question.getRequireSelect());
            parameterSources[i].addValue("problemType", question.getProblemType().toString());
        }
        namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);

    }

    @Override
    public void deletePrimaryQuestionnaire(List<Integer> pqIdList) {
        String sql = "DELETE FROM primary_questionnaire WHERE pq_id = :pqId";
        Map<String, Object> map = new HashMap<>();
        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[pqIdList.size()];
        for (int i = 0; i < pqIdList.size(); i++) {
            Integer pqId = pqIdList.get(i);
            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("pqId", pqId);
        }
        namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);
    }

    @Override
    public void deleteQuestionByPqId(List<Integer> idList) {
        String sql = "DELETE FROM question WHERE pq_id = :id";
        deleteQuestion(sql, idList);
    }
    @Override
    public void deleteQuestionByQId(List<Integer> idList) {
        String sql = "DELETE FROM question WHERE q_id = :id";
        deleteQuestion(sql, idList);
    }

    @Override
    public void createAnswerResults(List<UserInfoRequest> userInfoRequest) {
        String sql = "INSERT INTO pq_user_info (pq_id, user_name, user_phone, user_email, " +
                "user_age, answer, problem_type, q_problem, created_time) " +
                "VALUES (:pqId, :userName, :userPhone, :userEmail, :userAge, :answer, :problemType, :qProblem, :createdTime) ";
        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[userInfoRequest.size()];
        for(int i = 0; i< userInfoRequest.size(); i++){
            UserInfoRequest userInfoRequest1 = userInfoRequest.get(i);
            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("pqId", userInfoRequest1.getPqId());
            parameterSources[i].addValue("userName", userInfoRequest1.getName());
            parameterSources[i].addValue("userPhone", userInfoRequest1.getPhone());
            parameterSources[i].addValue("userEmail", userInfoRequest1.getEmail());
            parameterSources[i].addValue("userAge", userInfoRequest1.getAge());
            parameterSources[i].addValue("answer", userInfoRequest1.getAnswer());
            parameterSources[i].addValue("problemType", userInfoRequest1.getQProblemType().toString());
            parameterSources[i].addValue("qProblem", userInfoRequest1.getQProblem());
            parameterSources[i].addValue("createdTime", new Date());
        }
        log.info("params size:{}", parameterSources);
        namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);
    }

    @Override
    public List<UserInfoOne> getQuestionnaireAnswer(Integer pqId) {
        String sql = "SELECT user_id, pq_id, user_name, created_time FROM pq_user_info WHERE pq_id = :pqId";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pqId", pqId);
        List<UserInfoOne> userInfoOneList = namedParameterJdbcTemplate.query(sql, map, new UserInfoOneRowMapper());
        return userInfoOneList;
    }

    @Override
    public List<PrimaryQuestionnaire> getUserPrimaryQuestionnaires(QuestionnaireQueryParam questionnaireQueryParam) {
        // 一開始的查詢條件就包含以 搜尋時間 作為查詢
        String sql = "SELECT pq_id, pq_title, pq_status, pq_created_date, pq_ended_date," +
                " pq_description FROM primary_questionnaire WHERE pq_created_date >= :createdDate" +
                " AND pq_ended_date <= :endedDate AND pq_status != 'UNPUBLISHED' ";
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
    public Integer countUserQuestionnaires(QuestionnaireQueryParam questionnaireQueryParam) {
        String sql = " SELECT count(*) FROM primary_questionnaire " +
                "WHERE pq_created_date >= :createdDate AND pq_ended_date <= :endedDate AND pq_status != 'UNPUBLISHED'  ";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("createdDate", questionnaireQueryParam.getBeginDate());
        map.put("endedDate", questionnaireQueryParam.getEndedDate());
        //模糊查詢
        addFilteringSql(sql, map, questionnaireQueryParam);
        return namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
    }

    private String addFilteringSql(String sql, Map<String, Object> map, QuestionnaireQueryParam questionnaireQueryParam) {
        // 判斷是否有 搜尋標題 的模糊查詢
        if (questionnaireQueryParam.getSearchTitle() != null) {
            sql = sql + " AND pq_title LIKE :pqTitle";
            map.put("pqTitle", "%" + questionnaireQueryParam.getSearchTitle() + "%");
        }
        return sql;
    }

    private void deleteQuestion(String sql, List<Integer> idList) {
        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[idList.size()];
        for (int i = 0; i < idList.size(); i++) {
            Integer id = idList.get(i);
            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("id", id);
        }
        namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);
    }
}
