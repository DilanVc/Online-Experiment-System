package cc.mrbird.febs.exercise.entity;


import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.data.annotation.Id;

/**
 *  Entity
 *
 * @author liuxin
 * @date 2021-02-20 11:17:49
 */
@Data
@TableName("t_chapter_question")
public class ChapterQuestion {

    /**
     * 
     */
    @TableId("CHAPTER_ID")
    private Long chapterId;

    /**
     * 
     */
    @TableId("QUESTION_ID")
    private Long questionId;

}
