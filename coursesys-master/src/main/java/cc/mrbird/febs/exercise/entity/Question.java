package cc.mrbird.febs.exercise.entity;

import java.util.Date;
import java.util.List;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.web.multipart.MultipartFile;

/**
 *  Entity
 *
 * @author liuxin
 * @date 2021-02-20 13:28:37
 */
@Data
@TableName("t_question")
public class Question {

    /**
     * 问题ID
     */
    @TableId(value = "QUESTION_ID", type = IdType.AUTO)
    private Long questionId;

    /**
     * 所在章节
     */
    @TableField("CHAPTER_NAM1")
    private String chapterNam1;
    /**
     * 所属章节
     */
    @TableField("CHAPTER_ID")
    private Long chapterId;

    /**
     * 问题标题
     */

    @TableField("QUESTION_TITLE")
    private String questionTitle;

    /**
     * 问题内容
     */
    @TableField("QUESTION_CONTENT")
    private String questionContent;

    /**
     * 开始时间
     */
    @TableField("QUESTION_START")
    private String questionStart;

    /**
     * 结束时间
     */
    @TableField("QUESTION_END")
    private String questionEnd;

    /**
     * 答案结果
     */
    @TableField("QUESTION_RESULT")
    private String questionResult;


    /**
     * 结果的类型：0为图片，1为console
     */
    @TableField("QUESTION_RESULT_TYPE")
    private String questionResultType;

    /**
     * 创建人
     */
    @TableField("QUESTION_CREATE_USER")
    private Long questionCreateUser;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
    private Date modifyTime;

    /**
     * 是否删除
     */
    @TableField("QUESTION_IS_DELETE")
    private String questionIsDelete;

    /**
     * 删除时间
     */
    @TableField("DELETE_TIME")
    private Date deleteTime;

    /**
     * 菜单对应的名称 chapterName
     */
    private transient String chapterName;

    /**
     * 创建人的姓名 createName
     */
    private transient String createName;

    /**
     * 可用图片集合 testPic
     */
    private transient List<QuestionFile> testPic;

    /**
     * 结果的图片 resultPic
     */
    private transient MultipartFile resultPic;


    /**
     * 题目的测试图片 testPic1
     */
    private  transient  List<MultipartFile> testPic1;


}
