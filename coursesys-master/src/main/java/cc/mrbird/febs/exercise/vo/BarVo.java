package cc.mrbird.febs.exercise.vo;

import cc.mrbird.febs.system.vo.QuestionBarVo;
import lombok.Data;

import java.util.List;

@Data
public class BarVo extends QuestionBarVo {
    private List<String> chapterNam1;
    private List<Integer> totalNumber;

    private List<Integer> rightNumber;

}
