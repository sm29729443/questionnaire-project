package com.example.questionnaireproject.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * ClassName: UserInfoOne
 * Package: com.example.questionnaireproject.model
 */
@Setter
@Getter
public class UserInfoOne {
    @JsonProperty("user_id")
    Integer userId;
    @JsonProperty("pq_id")
    Integer pqId;
    @JsonProperty("user_name")
    String userName;
    @JsonProperty("created_date")
    Date createdDate;
}
