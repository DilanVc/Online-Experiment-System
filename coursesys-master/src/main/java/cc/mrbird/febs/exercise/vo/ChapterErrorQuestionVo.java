package cc.mrbird.febs.exercise.vo;

import lombok.Data;

import java.util.List;

@Data
public class ChapterErrorQuestionVo {
    private String chapterNam1;
    private Integer  questionId;
    private Integer  times;
    private String status;
    private String questionTitle;
    private Integer chapterId;
}
