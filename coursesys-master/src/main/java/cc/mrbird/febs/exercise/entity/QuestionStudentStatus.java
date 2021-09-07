package cc.mrbird.febs.exercise.entity;

import java.util.Date;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 *  Entity
 *
 * @author liuxin
 * @date 2021-03-10 19:39:53
 */
@Data
@TableName("t_question_student_status")
public class QuestionStudentStatus {

    /**
     * 
     */
    @TableId(value = "STATUS_ID", type = IdType.AUTO)
    private Long statusId;

    /**
     * 
     */
    @TableField("QUESTION_ID")
    private Long questionId;

    /**
     * 
     */
    @TableField("STUDENT_ID")
    private Long studentId;

    /**
     * 
     */
    @TableField("STATUS")
    private String status;

    /**
     * 次数
     */
    @TableField("TIMES")
    private Integer times;

    /**
     * 
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 
     */
    @TableField("MODIFY_TIME")
    private Date modifyTime;

}
