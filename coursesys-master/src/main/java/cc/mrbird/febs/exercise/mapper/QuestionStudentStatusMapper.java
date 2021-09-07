package cc.mrbird.febs.exercise.mapper;

import cc.mrbird.febs.exercise.entity.QuestionStudentStatus;
import cc.mrbird.febs.exercise.vo.BarVo;
import cc.mrbird.febs.exercise.vo.ChapterErrorQuestionVo;
import cc.mrbird.febs.exercise.vo.ErrorQuestionTimesBarVo;
import cc.mrbird.febs.exercise.vo.QuestionBarVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 *  Mapper
 *
 * @author liuxin
 * @date 2021-02-23 18:09:21
 */
public interface QuestionStudentStatusMapper extends BaseMapper<QuestionStudentStatus> {

//    @Select("SELECT \n" +
//            "  t2.CHAPTER_ID,\n" +
//            "\tt2.TOTAL_NUMBER,\n" +
//            "\tt2.question,\n" +
//            "\tt1.RIGHT_NUMBER\n" +
//            " from (SELECT\n" +
//            "\t\tCHAPTER_ID,\n" +
//            "\t\tCOUNT(*) as TOTAL_NUMBER,\n" +
//            "\t\tgroup_concat( QUESTION_ID SEPARATOR ',' ) AS question \n" +
//            "\tFROM\n" +
//            "\t\tt_chapter_question cq \n" +
//            "\tGROUP BY\n" +
//            "\t\tcq.CHAPTER_ID ) t2 LEFT JOIN (\n" +
//            "\tSELECT\n" +
//            "\t\tu.CHAPTER_ID,\n" +
//            "\t\tcount(*) as RIGHT_NUMBER\n" +
//            "\tFROM\n" +
//            "\t\tt_chapter_question u\n" +
//            "\t\tLEFT JOIN t_question_student_status u1 ON u.QUESTION_ID = u1.QUESTION_ID \n" +
//            "\tWHERE\n" +
//            "\t\tu1.`STATUS` = 'right' and u1.STUDENT_ID = #{userId}\n" +
//            "\tGROUP BY\n" +
//            "\t\tu.CHAPTER_ID \n" +
//            "\t\t) t1 on t2.CHAPTER_ID = t1.CHAPTER_ID")
//            @Select("SELECT\n" +
//                    "\tt2.CHAPTER_ID,\n" +
//                    "\tt2.TOTAL_NUMBER,\n" +
//                    "\tt2.question,\n" +
//                    "\tt1.RIGHT_NUMBER ,\n" +
//                    "\tt1.TIMES\n" +
//                    "FROM\n" +
//                    "\t( SELECT CHAPTER_ID, COUNT(*) AS TOTAL_NUMBER, group_concat( QUESTION_ID SEPARATOR ',' ) AS question FROM t_chapter_question cq GROUP BY cq.CHAPTER_ID ) t2\n" +
//                    "\tLEFT JOIN (\n" +
//                    "\tSELECT\n" +
//                    "\t\tu.CHAPTER_ID,\n" +
//                    "\t\tcount(*) AS RIGHT_NUMBER,\n" +
//                    "\t\tu1.TIMES \n" +
//                    "\tFROM\n" +
//                    "\t\tt_chapter_question u\n" +
//                    "\t\tLEFT JOIN t_question_student_status u1 ON u.QUESTION_ID = u1.QUESTION_ID \n" +
//                    "\tWHERE\n" +
//                    "\t\tu1.`STATUS` = 'success' \n" +
//                    "\t\tAND u1.STUDENT_ID = #{userId}\n" +
//                    "\tGROUP BY\n" +
//                    "\tu.CHAPTER_ID \n" +
//                    "\t) t1 ON t2.CHAPTER_ID = t1.CHAPTER_ID")




//    @Select("SELECT\n" +
//            "\tt2.CHAPTER_ID,\n" +
//            "\tt2.TOTAL_NUMBER,\n" +
//            "\tt2.question,\n" +
//            "\tt1.RIGHT_NUMBER,\n" +
//            "\tt1.TIMES \n" +
//            "FROM\n" +
//            "\t( SELECT CHAPTER_ID, COUNT(*) AS TOTAL_NUMBER, group_concat( QUESTION_ID SEPARATOR ',' ) AS question FROM t_chapter_question cq GROUP BY cq.CHAPTER_ID ) t2\n" +
//            "\tLEFT JOIN (\n" +
//            "\tSELECT\n" +
//            "\t\tu.CHAPTER_ID,\n" +
//            "\t\tcount(*) AS RIGHT_NUMBER,\n" +
//            "\t\tu1.TIMES \n" +
//            "\tFROM\n" +
//            "\t\tt_chapter_question u\n" +
//            "\t\tLEFT JOIN t_question_student_status u1 ON u.QUESTION_ID = u1.QUESTION_ID \n" +
//            "\tWHERE\n" +
//            "\t\tu1.`STATUS` = 'success' \n" +
//            "\t\tAND u1.STUDENT_ID = #{userId}\n" +
//            "\tGROUP BY\n" +
//            "\t\tu.CHAPTER_ID,\n" +
//            "\tu1.TIMES \n" +
//            "\t) t1 ON t2.CHAPTER_ID = t1.CHAPTER_ID")

    @Select("SELECT\n" +
            "\tt2.CHAPTER_NAM1,\n" +
            "\tt1.RIGHT_NUMBER,\n" +
            "\tt2.TOTAL_NUMBER\n" +
            "FROM\n" +
            "( SELECT CHAPTER_NAM1, count(*) AS TOTAL_NUMBER FROM t_question GROUP BY CHAPTER_NAM1 ) t2\n" +
            "\t\n" +
            "\tLEFT JOIN (\n" +
            "\tSELECT\n" +
            "\t\tCHAPTER_NAM1,\n" +
            "\t\tcount(*) AS RIGHT_NUMBER \n" +
            "\tFROM\n" +
            "\t\tt_question\n" +
            "\t\t\n" +
            "\t\tLEFT JOIN t_question_student_status ON t_question_student_status.QUESTION_ID = t_question.QUESTION_ID \n" +
            "\tWHERE\n" +
            "\t\tt_question_student_status.`STATUS` = 'success' \n" +
            "\t\tAND t_question_student_status.STUDENT_ID = #{userId} \n" +
            "\tGROUP BY\n" +
            "\t\tCHAPTER_NAM1 \n" +
            "\t) t1 ON t1.CHAPTER_NAM1 = t2.CHAPTER_NAM1")

    List<QuestionBarVo> getChapterRight(@Param("userId") Long userId);


     @Select("SELECT\n" +
             "\t* \n" +
             "FROM\n" +
             "\tt_question_student_status \n" +
             "WHERE\n" +
             "\tQUESTION_ID =#{questionId} \n" +
             "\tAND STUDENT_ID=#{studentId}")
    QuestionStudentStatus findQuestionByStudentIdAndQuestionID(@Param("questionId") Long questionId,@Param("studentId") Long studentId);


//@Select("SELECT\n" +
//        "\t\t\t\t\tt2.CHAPTER_ID,\n" +
//        "\t\t\t\t\tt1.QUESTION_ID,\n" +
//        "\t\t\t\t\tt1.TIMES,\n" +
//        "\t\t\t\t t1.`STATUS` ,\n" +
//        "\t\t\t\t t3.MENU_NAME\n" +
//        "\t\t\t\t\tFROM\n" +
//        "\t\t\t\t\tt_question_student_status t1,t_chapter_question t2 ,t_menu t3\n" +
//        "\t\t\t\t\tWHERE\n" +
//        "\t\t\t\t\tt1.STUDENT_ID = #{userId}\n" +
//        "\t\t\t\tAND t1.`STATUS` = \"error\"\n" +
//        "\t\t\t\tAND t1.QUESTION_ID = t2.QUESTION_ID\n" +
//        "\t\t\t\tand t3.MENU_ID = t2.CHAPTER_ID")



//    SELECT
//    t2.CHAPTER_ID,
//    t1.QUESTION_ID,
//    t1.TIMES,
//    t1.`STATUS` ,
//    t3.MENU_NAME
//            FROM
//    t_question_student_status t1,t_chapter_question t2 ,t_menu t3
//    WHERE
//    t1.STUDENT_ID = 1
//    AND t1.`STATUS` = "error"
//    AND t1.QUESTION_ID = t2.QUESTION_ID
//    and t3.MENU_ID = t2.CHAPTER_ID




//@Select("SELECT\n" +
//        "     t2.CHAPTER_NAM1,\n" +
//        "\t\t t2.CHAPTER_ID,\n" +
//        "     t1.QUESTION_ID,\n" +
//        "        t1.TIMES,\n" +
//        "       t1.`STATUS` ,\n" +
//        "       t2.QUESTION_TITLE\n" +
//        "      FROM\n" +
//        "       t_question_student_status t1,t_question t2 \n" +
//        "       WHERE\n" +
//        "        t1.STUDENT_ID = #{userId}\n" +
//        "        AND t1.`STATUS` ='error'\n" +
//        "       AND t1.QUESTION_ID = t2.QUESTION_ID\n" +
//        "\t\t\t ORDER BY CHAPTER_ID")

@Select("SELECT\n" +
        "\tx1.CHAPTER_NAM1,\n" +
        "\tx1.CHAPTER_ID,\n" +
        "\tx1.QUESTION_ID,\n" +
        "\n" +
        "\tx1.QUESTION_TITLE ,\n" +
        "\tx2.TIMES,\n" +
        "\tx2.`STATUS`\n" +
        "FROM\n" +
        "\t( SELECT * FROM t_question ) x1\n" +
        "\tLEFT JOIN \n" +
        "\t(SELECT\n" +
        "\tt2.CHAPTER_NAM1,\n" +
        "\tt2.CHAPTER_ID,\n" +
        "\tt1.QUESTION_ID,\n" +
        "\tt1.TIMES,\n" +
        "\tt1.`STATUS`,\n" +
        "\tt2.QUESTION_TITLE \n" +
        "FROM\n" +
        "\tt_question_student_status t1,\n" +
        "\tt_question t2 \n" +
        "WHERE\n" +
        "\tt1.STUDENT_ID = #{userId} \n" +
        "\tAND t1.`STATUS` = 'error' \n" +
        "\tAND t1.QUESTION_ID = t2.QUESTION_ID \n" +
        "ORDER BY\n" +
        "\tCHAPTER_ID) x2 ON x1.QUESTION_ID = x2.QUESTION_ID")
    List<ChapterErrorQuestionVo> getChapterErrorByUserId(@Param("userId")Long userId);



//@Select("SELECT\n" +
//        "\tt_question.QUESTION_TITLE,\n" +
//        "\tt_question_student_status.TIMES \n" +
//        "FROM\n" +
//        "\tt_question_student_status,\n" +
//        "\tt_question \n" +
//        "WHERE\n" +
//        "\tt_question_student_status.`STATUS` = 'error' \n" +
//        "\tAND t_question_student_status.STUDENT_ID = #{userId} \n" +
//        "\tAND t_question.QUESTION_ID = t_question_student_status.QUESTION_ID")


    @Select("SELECT\n" +
            "\tx1.QUESTION_TITLE ,\n" +
            "\tx2.TIMES\n" +
            "\t\n" +
            "FROM\n" +
            "\t( SELECT * FROM t_question ) x1\n" +
            "\tLEFT JOIN \n" +
            "\t(SELECT\n" +
            "\tt2.CHAPTER_NAM1,\n" +
            "\tt2.CHAPTER_ID,\n" +
            "\tt1.QUESTION_ID,\n" +
            "\tt1.TIMES,\n" +
            "\tt1.`STATUS`,\n" +
            "\tt2.QUESTION_TITLE \n" +
            "FROM\n" +
            "\tt_question_student_status t1,\n" +
            "\tt_question t2 \n" +
            "WHERE\n" +
            "\tt1.STUDENT_ID = #{userId} \n" +
            "\tAND t1.`STATUS` = 'error' \n" +
            "\tAND t1.QUESTION_ID = t2.QUESTION_ID \n" +
            "ORDER BY\n" +
            "\tCHAPTER_ID) x2 ON x1.QUESTION_ID = x2.QUESTION_ID")
    List<ErrorQuestionTimesBarVo>getErrorQuestionTimesBarVoById(@Param("userId") Long userId);
}
