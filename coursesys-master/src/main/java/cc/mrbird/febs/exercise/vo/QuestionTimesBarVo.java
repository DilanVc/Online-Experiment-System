package cc.mrbird.febs.exercise.vo;

import lombok.Data;

import java.util.List;

@Data
public class QuestionTimesBarVo extends ErrorQuestionTimesBarVo {

    private List<String> questionTitles;
    private List<Integer> timess;
}
