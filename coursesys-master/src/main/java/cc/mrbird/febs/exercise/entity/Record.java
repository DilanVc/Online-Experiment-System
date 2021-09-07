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
 * @date 2021-02-22 19:17:30
 */
@Data
@TableName("t_record")
public class Record {

    /**
     * 
     */
    @TableId(value = "RECORD_ID", type = IdType.AUTO)
    private Long recordId;

    /**
     * 
     */
    @TableField("STUDENT_ID")
    private Long studentId;

    /**
     * 
     */
    @TableField("QUESTION_ID")
    private Long questionId;

    /**
     * 学生的代码
     */
    @TableField("QUESTION_ANSWER")
    private String questionAnswer;

    /**
     * 编译的结果
     */
    @TableField("RESULT")
    private String result;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

}
