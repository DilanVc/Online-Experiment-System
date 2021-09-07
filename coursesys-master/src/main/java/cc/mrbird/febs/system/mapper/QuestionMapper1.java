package cc.mrbird.febs.system.mapper;

import cc.mrbird.febs.exercise.entity.Question;
import cc.mrbird.febs.system.vo.BarVo;
import cc.mrbird.febs.system.vo.QuestionBarVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
//import com.sun.tools.javac.util.List;

public interface QuestionMapper1 extends BaseMapper<BarVo> {
    @Select("SELECT\n" +
            "\tu.CHAPTER_ID,count(*) \n" +
            "FROM\n" +
            "  t_chapter_question u\n" +
            "\tleft join t_question_student_status u1 on u.QUESTION_ID = u1.QUESTION_ID\n" +
            "\twhere u1.`STATUS`='right'\n" +
            "\tGROUP BY u.CHAPTER_ID")
    public List<QuestionBarVo> findAllQuestionBarVo();
}
