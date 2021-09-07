package cc.mrbird.febs.exercise.service.impl;

import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.exercise.entity.Question;
import cc.mrbird.febs.exercise.entity.QuestionFile;
import cc.mrbird.febs.exercise.mapper.QuestionMapper;
import cc.mrbird.febs.exercise.service.IQuestionService;
import cc.mrbird.febs.system.entity.Role;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import lombok.RequiredArgsConstructor;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *  Service实现
 *
 * @author liuxin
 * @date 2021-02-20 13:28:37
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {

    private final QuestionMapper questionMapper;

    @Override
    public IPage<Question> findQuestions(QueryRequest request, Question question) {
        LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        Page<Question> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<Question> findQuestions(Question question) {
	    LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<>();
		// TODO 设置查询条件
		return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createQuestion(Question question) {
        question.setCreateTime(new Date());
        this.save(question);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateQuestion(Question question) {
        question.setModifyTime(new Date());
        this.saveOrUpdate(question);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteQuestion(String questionIds) {
        List<String> list = Arrays.asList(questionIds.split(StringPool.COMMA));
        this.baseMapper.delete(new QueryWrapper<Question>().lambda().in(Question::getQuestionId, list));
	}

    @Override
    public List<Question> getQuestionDetailList() {
        return this.questionMapper.getQuestionDetailList();
    }

    @Override
    public IPage<Question> getQuestionDetailList(QueryRequest request,Question question) {
        Page<Question> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.questionMapper.getQuestionDetailListPage(page,question.getQuestionTitle());
    }

    @Override
    public List<Question> getQuestionDetailListByChapterId(Long chapterId) {
        return this.questionMapper.getQuestionDetailListByChapterId(chapterId);
    }

    @Override
    public void setQuestionFile(QuestionFile questionFile) {

         this.questionMapper.setQuestionFile(questionFile);
    }

    @Override
    public void updataMeau(Long ChapterId, String questionId) {
        this.questionMapper.updataMeau(ChapterId, questionId);
    }


}
