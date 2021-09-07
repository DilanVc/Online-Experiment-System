package cc.mrbird.febs.exercise.mapper;

import cc.mrbird.febs.exercise.entity.Question;
import cc.mrbird.febs.exercise.entity.QuestionFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 *  Mapper
 *
 * @author liuxin
 * @date 2021-02-20 13:28:37
 */
public interface QuestionMapper extends BaseMapper<Question> {

    @Select("SELECT\n" +
            "\tq.* ,\n" +
            "\tu.USERNAME as CREATE_NAME,\n" +
            "\tm.MENU_NAME AS CHAPTER_NAME\n" +
            "FROM\n" +
            "\tt_question q\n" +
            "\tLEFT JOIN t_menu m ON q.CHAPTER_ID = m.MENU_ID\n" +
            "\tLEFT JOIN t_user u ON q.QUESTION_CREATE_USER = u.USER_ID\n" +
            "\tWHERE q.QUESTION_IS_DELETE = 0")
    List<Question> getQuestionDetailList();

    @Select({"<script>",
            "SELECT\n" +
            "\tq.* ,\n" +
            "\tu.USERNAME as CREATE_NAME,\n" +
            "\tm.MENU_NAME AS CHAPTER_NAME\n" +
            "FROM\n" +
            "\tt_question q\n" +
            "\tLEFT JOIN t_menu m ON q.CHAPTER_ID = m.MENU_ID\n" +
            "\tLEFT JOIN t_user u ON q.QUESTION_CREATE_USER = u.USER_ID\n" +
            "\tWHERE q.QUESTION_IS_DELETE = 0" +
            " <when test='questionTitle!=null'> ",
            "       AND q.QUESTION_TITLE LIKE concat('%',#{questionTitle},'%') ",
            " </when> ",
            " </script> "})


    IPage<Question> getQuestionDetailListPage(Page page,@Param(value = "questionTitle") String questionTitle);

    @Select("SELECT\n" +
            "\tq.* ,\n" +
            "\tu.USERNAME as CREATE_NAME,\n" +
            "\tm.MENU_NAME AS CHAPTER_NAME\n" +
            "FROM\n" +
            "\tt_question q\n" +
            "\tLEFT JOIN t_menu m ON q.CHAPTER_ID = m.MENU_ID\n" +
            "\tLEFT JOIN t_user u ON q.QUESTION_CREATE_USER = u.USER_ID\n" +
            "\tWHERE q.QUESTION_IS_DELETE = 0 " +
            "and q.CHAPTER_ID = #{chapterId}")
    List<Question> getQuestionDetailListByChapterId(@Param(value = "chapterId") Long chapterId);

    @Insert("INSERT INTO t_question_file ( QUESTION_ID, FILE_PATH, FILE_NAME, CREATE_TIME, CREATE_USER )\n" +
            "VALUES\n" +
            "\t(#{questionId},#{filePath},#{fileName},#{createTime},#{createUser}\t)")
    void setQuestionFile(QuestionFile questionFile);


    @Update("UPDATE t_menu\n" +
            "set URL=#{questionId}\n" +
            "WHERE MENU_ID=#{chapterId}")
    void updataMeau(Long chapterId, String questionId) ;


}
