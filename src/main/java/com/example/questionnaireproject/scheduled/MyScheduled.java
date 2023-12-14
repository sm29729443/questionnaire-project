package com.example.questionnaireproject.scheduled;

import com.example.questionnaireproject.model.PrimaryQuestionnaire;
import com.example.questionnaireproject.model.Question;
import com.example.questionnaireproject.rowmapper.QuestionRowMapper;
import com.example.questionnaireproject.rowmapper.QuestionnaireRowMapper;
import com.example.questionnaireproject.rowmapper.QuestionnaireRowMapperForStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class MyScheduled {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

//    @Scheduled(cron = "0 3 0 * * ?")
    @Scheduled(fixedRate = 15000)
    public void test() {
        String sql = "SELECT pq_id, pq_title, pq_status, pq_created_date, pq_ended_date, pq_description FROM primary_questionnaire WHERE pq_status = 'UNSTARTED' ";
        Map<String, Object> map = new HashMap<String, Object>();
        List<PrimaryQuestionnaire> questionList = namedParameterJdbcTemplate.query(sql, map, new QuestionnaireRowMapper());
        LocalDate currentDate = LocalDate.now();
        // 將 LocalDate 轉換為整數形式
        int formattedDate = Integer.parseInt(currentDate.format(java.time.format.DateTimeFormatter.BASIC_ISO_DATE));

        for (PrimaryQuestionnaire pq : questionList) {
            // 將 java.sql.Date 轉換為整數形式
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            int pqTime = Integer.parseInt(dateFormat.format(pq.getPqCreatedDate().getTime()));
            log.info("sql time:{}", pqTime);
            log.info("current date:{}", formattedDate);
            if ( pqTime == formattedDate ) {
                log.info("UPDATE SQL 執行");
                String sql2 = "UPDATE primary_questionnaire SET pq_status = 'PROGRESS' WHERE pq_id = :pqId";
                Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("pqId", pq.getPqId());
                namedParameterJdbcTemplate.update(sql2, map2);
            }
        }


    }
}
