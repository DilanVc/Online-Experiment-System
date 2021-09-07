package cc.mrbird.febs.exercise.service;

import cc.mrbird.febs.exercise.entity.Question;

import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.exercise.entity.QuestionFile;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *  Service接口
 *
 * @author liuxin
 * @date 2021-02-20 13:28:37
 */
public interface IQuestionService extends IService<Question> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param question question
     * @return IPage<Question>
     */
    IPage<Question> findQuestions(QueryRequest request, Question question);

    /**
     * 查询（所有）
     *
     * @param question question
     * @return List<Question>
     */
    List<Question> findQuestions(Question question);

    /**
     * 新增
     *
     * @param question question
     */
    void createQuestion(Question question);

    /**
     * 修改
     *
     * @param question question
     */
    void updateQuestion(Question question);

    /**
     * 删除
     *
     * @param String questionIds
     */
    void deleteQuestion(String questionIds);

    /*****************************新增的dao层操作***************************/

    /**
     * 查询实验详情（包括创建人、所属菜单）
     *
     * @return List<Question>
     */
    List<Question> getQuestionDetailList();

    /**
     * 查询实验详情 分页（包括创建人、所属菜单）
     *
     * @return IPage<Question>
     */
    IPage<Question> getQuestionDetailList(QueryRequest request,Question question);

    /**
    *  根据chapterID查看所有的实验
    *  @param Long chapterId
    *  @return List<Question>
    */
    List<Question> getQuestionDetailListByChapterId(Long chapterId);
    /**
     * 将测试图片存储在t_question_file中
     *
     */
    void  setQuestionFile(QuestionFile questionFile);

    /**
     * 更新t_Mean表
     */
    void updataMeau(Long ChapterId,String questionId);
}
