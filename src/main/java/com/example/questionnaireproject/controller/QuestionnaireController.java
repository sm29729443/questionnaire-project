package com.example.questionnaireproject.controller;

import com.example.questionnaireproject.constants.QuestionnaireStatus;
import com.example.questionnaireproject.dto.QuestionRequest;
import com.example.questionnaireproject.dto.QuestionnaireQueryParam;
import com.example.questionnaireproject.dto.QuestionnaireSessionRequest;
import com.example.questionnaireproject.model.PrimaryQuestionnaire;
import com.example.questionnaireproject.model.Question;
import com.example.questionnaireproject.service.QuestionnaireService;
import com.example.questionnaireproject.util.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;


@RestController
@Slf4j
@Validated
@CrossOrigin(origins = "http://localhost:3000",allowCredentials = "true")
//@CrossOrigin(allowCredentials = "true")
public class QuestionnaireController {
    @Autowired
    private QuestionnaireService questionnaireService;

    // 問卷列表查詢功能
    @GetMapping("/primaryQuestionnaires")
    public ResponseEntity<Page<PrimaryQuestionnaire>> getPrimaryQuestionnaires(
            @RequestParam(defaultValue = "2000-01-01") @NotNull @DateTimeFormat(pattern="yyyy-MM-dd") Date beginDate,
            @RequestParam(defaultValue = "2100-12-31") @NotNull @DateTimeFormat(pattern="yyyy-MM-dd") Date endedDate,
            @RequestParam(required = false) String searchTitle,
            @RequestParam(defaultValue = "10") @Min(1) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset) {
        QuestionnaireQueryParam questionnaireQueryParam = new QuestionnaireQueryParam();
        questionnaireQueryParam.setBeginDate(beginDate);
        questionnaireQueryParam.setEndedDate(endedDate);
        questionnaireQueryParam.setSearchTitle(searchTitle);
        questionnaireQueryParam.setLimit(limit);
        questionnaireQueryParam.setOffset(offset);
        //查詢問卷內容
        List<PrimaryQuestionnaire> primaryQuestionnaireList = questionnaireService.getPrimaryQuestionnaires(questionnaireQueryParam);
        //查詢問卷總數
        Integer total = questionnaireService.countQuestionnaires(questionnaireQueryParam);
        Page<PrimaryQuestionnaire> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(primaryQuestionnaireList);
        return ResponseEntity.status(HttpStatus.OK).body(page);

    }

    //將 問卷及子問題 儲存在 session
    @PostMapping("/session/loadQuestionnaire")
    public ResponseEntity<?> questionLoadToSession(
            @RequestBody @Valid QuestionnaireSessionRequest questionnaireSessionRequest,
            HttpSession session
            ) {
        session.setAttribute("quTitle", questionnaireSessionRequest.getQuTitle());
        session.setAttribute("quDescription", questionnaireSessionRequest.getQuDescription());
        session.setAttribute("beginDate", questionnaireSessionRequest.getBeginDate());
        session.setAttribute("endedDate", questionnaireSessionRequest.getEndedDate());
        session.setAttribute("questionList", questionnaireSessionRequest.getQuestionList());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    //新增問卷問題

    @PostMapping("/primaryQuestionnaires")
    public ResponseEntity<?> readQuestionnaire(HttpSession session, @RequestParam(defaultValue = "UNPUBLISHED")QuestionnaireStatus status) {
        PrimaryQuestionnaire primaryQuestionnaire = new PrimaryQuestionnaire();
        primaryQuestionnaire.setPqTitle(session.getAttribute("quTitle").toString());
        primaryQuestionnaire.setPqDescription(session.getAttribute("quDescription").toString());
        primaryQuestionnaire.setPqCreatedDate(new Date(session.getAttribute("beginDate").toString()));
        primaryQuestionnaire.setPqEndedDate(new Date(session.getAttribute("endedDate").toString()));
        primaryQuestionnaire.setPqStatus(status);
        List<QuestionRequest> list = (List<QuestionRequest>) session.getAttribute("questionList");
        questionnaireService.createPrimaryQuestionnaire(primaryQuestionnaire, list);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/session/clear")
    public void sessionClear() {

    }

}
