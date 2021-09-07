package cc.mrbird.febs.exercise.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.exercise.entity.ChapterQuestion;
import cc.mrbird.febs.exercise.service.IChapterQuestionService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 *  Controller
 *
 * @author liuxin
 * @date 2021-02-20 11:17:49
 */
@Slf4j
@Validated
@Controller
@RequiredArgsConstructor
public class ChapterQuestionController extends BaseController {

    private final IChapterQuestionService chapterQuestionService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "chapterQuestion")
    public String chapterQuestionIndex(){
        return FebsUtil.view("chapterQuestion/chapterQuestion");
    }

    @GetMapping("chapterQuestion")
    @ResponseBody
    @RequiresPermissions("chapterQuestion:list")
    public FebsResponse getAllChapterQuestions(ChapterQuestion chapterQuestion) {
        return new FebsResponse().success().data(chapterQuestionService.findChapterQuestions(chapterQuestion));
    }

    @GetMapping("chapterQuestion/list")
    @ResponseBody
    @RequiresPermissions("chapterQuestion:list")
    public FebsResponse chapterQuestionList(QueryRequest request, ChapterQuestion chapterQuestion) {
        Map<String, Object> dataTable = getDataTable(this.chapterQuestionService.findChapterQuestions(request, chapterQuestion));
        return new FebsResponse().success().data(dataTable);
    }

    @ControllerEndpoint(operation = "新增ChapterQuestion", exceptionMessage = "新增ChapterQuestion失败")
    @PostMapping("chapterQuestion")
    @ResponseBody
    @RequiresPermissions("chapterQuestion:add")
    public FebsResponse addChapterQuestion(@Valid ChapterQuestion chapterQuestion) {
        this.chapterQuestionService.createChapterQuestion(chapterQuestion);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "删除ChapterQuestion", exceptionMessage = "删除ChapterQuestion失败")
    @GetMapping("chapterQuestion/delete")
    @ResponseBody
    @RequiresPermissions("chapterQuestion:delete")
    public FebsResponse deleteChapterQuestion(ChapterQuestion chapterQuestion) {
        this.chapterQuestionService.deleteChapterQuestion(chapterQuestion);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改ChapterQuestion", exceptionMessage = "修改ChapterQuestion失败")
    @PostMapping("chapterQuestion/update")
    @ResponseBody
    @RequiresPermissions("chapterQuestion:update")
    public FebsResponse updateChapterQuestion(ChapterQuestion chapterQuestion) {
        this.chapterQuestionService.updateChapterQuestion(chapterQuestion);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改ChapterQuestion", exceptionMessage = "导出Excel失败")
    @PostMapping("chapterQuestion/excel")
    @ResponseBody
    @RequiresPermissions("chapterQuestion:export")
    public void export(QueryRequest queryRequest, ChapterQuestion chapterQuestion, HttpServletResponse response) {
        List<ChapterQuestion> chapterQuestions = this.chapterQuestionService.findChapterQuestions(queryRequest, chapterQuestion).getRecords();
        ExcelKit.$Export(ChapterQuestion.class, response).downXlsx(chapterQuestions, false);
    }
}
