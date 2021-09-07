package cc.mrbird.febs.exercise.service.impl;

import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.exercise.entity.QuestionFile;
import cc.mrbird.febs.exercise.mapper.QuestionFileMapper;
import cc.mrbird.febs.exercise.service.IQuestionFileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import lombok.RequiredArgsConstructor;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 *  Service实现
 *
 * @author liuxin
 * @date 2021-02-21 22:54:31
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class QuestionFileServiceImpl extends ServiceImpl<QuestionFileMapper, QuestionFile> implements IQuestionFileService {

    private final QuestionFileMapper questionFileMapper;

    @Override
    public IPage<QuestionFile> findQuestionFiles(QueryRequest request, QuestionFile questionFile) {
        LambdaQueryWrapper<QuestionFile> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        Page<QuestionFile> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<QuestionFile> findQuestionFiles(QuestionFile questionFile) {
	    LambdaQueryWrapper<QuestionFile> queryWrapper = new LambdaQueryWrapper<>();
		// TODO 设置查询条件
        queryWrapper.eq(QuestionFile::getQuestionId,questionFile.getQuestionId());
		return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createQuestionFile(QuestionFile questionFile) {
        this.save(questionFile);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateQuestionFile(QuestionFile questionFile) {
        this.saveOrUpdate(questionFile);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteQuestionFile(QuestionFile questionFile) {
        LambdaQueryWrapper<QuestionFile> wrapper = new LambdaQueryWrapper<>();
	    // TODO 设置删除条件
	    this.remove(wrapper);
	}
}
