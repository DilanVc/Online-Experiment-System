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
 * @date 2021-02-21 22:54:31
 */
@Data
@TableName("t_question_file")
public class QuestionFile {

    /**
     * 文件ID
     */
    @TableId(value = "FILE_ID", type = IdType.AUTO)
    private Long fileId;

    /**
     * 
     */
    @TableField("QUESTION_ID")
    private Long questionId;

    /**
     * 文件路径
     */
    @TableField("FILE_PATH")
    private String filePath;

    /**
     * 文件名称
     */
    @TableField("FILE_NAME")
    private String fileName;

    /**
     * 文件大小
     */
    @TableField("FILE_SIZE")
    private Double fileSize;

    /**
     * 上传人
     */
    @TableField("CREATE_USER")
    private Long createUser;

    /**
     * 上传时间
     */
    @TableField("CREATE_TIME")
    public Date createTime;

//    public void setCreateTime(Date Time) {
//      createTime =Time;
//    }
//
//    public void setCreateTime(Date time) {
//    }
}
