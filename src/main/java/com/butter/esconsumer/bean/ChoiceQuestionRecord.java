package com.butter.esconsumer.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ChoiceQuestionRecord保存用户在做题模块的做的每一道题的记录
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
//@TableName("xxxx choice_question_record_tbl") //consumer的数据库要和provider分开
public class ChoiceQuestionRecord {

    private Long cqrId;
    private Long erId;
    private Long userId;
    private Long qId;
    private boolean isright;
    private String usersAnswer;

}
