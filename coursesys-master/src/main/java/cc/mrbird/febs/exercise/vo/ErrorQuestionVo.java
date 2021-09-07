package cc.mrbird.febs.exercise.vo;


import lombok.Data;

import java.util.List;

@Data
public class ErrorQuestionVo {

  private List<Integer> ChapterId;
  private List<Integer> QuestionId;
  private List<Integer> Times;
  private List<Character> Status;

}
