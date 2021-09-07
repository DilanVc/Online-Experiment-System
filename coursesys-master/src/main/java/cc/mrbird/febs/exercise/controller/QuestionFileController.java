package cc.mrbird.febs.exercise.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.exercise.entity.QuestionFile;
import cc.mrbird.febs.exercise.service.IQuestionFileService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *  Controller
 *
 * @author liuxin
 * @date 2021-02-21 22:54:31
 */
@Slf4j
@Validated
@Controller
@RequiredArgsConstructor
public class QuestionFileController extends BaseController {

    private final IQuestionFileService questionFileService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "questionFile")
    public String questionFileIndex(){
        return FebsUtil.view("questionFile/questionFile");
    }

    @GetMapping("questionFile")
    @ResponseBody
    @RequiresPermissions("questionFile:list")
    public FebsResponse getAllQuestionFiles(QuestionFile questionFile) {

        return new FebsResponse().success().data(questionFileService.findQuestionFiles(questionFile));
    }

    @GetMapping("questionFile/list")
    @ResponseBody
    @RequiresPermissions("questionFile:list")
    public FebsResponse questionFileList(QueryRequest request, QuestionFile questionFile) {
        Map<String, Object> dataTable = getDataTable(this.questionFileService.findQuestionFiles(request, questionFile));
        return new FebsResponse().success().data(dataTable);
    }

    @ControllerEndpoint(operation = "新增QuestionFile", exceptionMessage = "新增QuestionFile失败")
    @PostMapping("questionFile")
    @ResponseBody
    @RequiresPermissions("question:add")
    public FebsResponse addQuestionFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new FebsException("上传失败，请选择文件");
        }
        System.out.println(file);
        // 开始上传操作
        String fileName = file.getOriginalFilename();
//        System.out.println(fileName);
        String path = System.getProperty("user.dir");  //获取项目相对路径
        String filePath = System.getProperty("user.dir") + "/target/classes/static/images/";
//        System.out.println(filePath);
        File dest = new File(filePath + fileName);
        try {
            file.transferTo(dest);
            QuestionFile questionFile = new QuestionFile();
            questionFile.setQuestionId((long) 10);
            questionFile.setCreateTime(new Date());
            questionFile.setCreateUser(getCurrentUser().getUserId());
            questionFile.setFileName(fileName);
            questionFile.setFilePath(filePath + fileName);
            questionFile.setFileSize((double) file.getSize());
//            System.out.println(questionFile);
            this.questionFileService.createQuestionFile(questionFile);
            return new FebsResponse().success();
            // 上传成功
        } catch (IOException e) {
            // 上传失败
            e.getMessage();
            return new FebsResponse().fail();
        }
    }

    @ControllerEndpoint(operation = "删除QuestionFile", exceptionMessage = "删除QuestionFile失败")
    @GetMapping("questionFile/delete")
    @ResponseBody
    @RequiresPermissions("questionFile:delete")
    public FebsResponse deleteQuestionFile(QuestionFile questionFile) {
        this.questionFileService.deleteQuestionFile(questionFile);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改QuestionFile", exceptionMessage = "修改QuestionFile失败")
    @PostMapping("questionFile/update")
    @ResponseBody
    @RequiresPermissions("questionFile:update")
    public FebsResponse updateQuestionFile(QuestionFile questionFile) {
        this.questionFileService.updateQuestionFile(questionFile);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改QuestionFile", exceptionMessage = "导出Excel失败")
    @PostMapping("questionFile/excel")
    @ResponseBody
    @RequiresPermissions("questionFile:export")
    public void export(QueryRequest queryRequest, QuestionFile questionFile, HttpServletResponse response) {
        List<QuestionFile> questionFiles = this.questionFileService.findQuestionFiles(queryRequest, questionFile).getRecords();
        ExcelKit.$Export(QuestionFile.class, response).downXlsx(questionFiles, false);
    }
}
