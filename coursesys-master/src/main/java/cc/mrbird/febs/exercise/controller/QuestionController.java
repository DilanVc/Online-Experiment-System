package cc.mrbird.febs.exercise.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.exercise.entity.ChapterQuestion;
import cc.mrbird.febs.exercise.entity.Question;
import cc.mrbird.febs.exercise.entity.QuestionFile;
import cc.mrbird.febs.exercise.service.IChapterQuestionService;
import cc.mrbird.febs.exercise.service.IQuestionFileService;
import cc.mrbird.febs.exercise.service.IQuestionService;
import cc.mrbird.febs.others.entity.Eximport;
import cc.mrbird.febs.system.entity.Menu;
import cc.mrbird.febs.system.entity.User;
import cc.mrbird.febs.system.service.IMenuService;
import cc.mrbird.febs.system.service.IUserService;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.wuwenze.poi.ExcelKit;
import com.wuwenze.poi.handler.ExcelReadHandler;
import com.wuwenze.poi.pojo.ExcelErrorField;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *  Controller
 *
 * @author liuxin
 * @date 2021-02-20 13:28:37
 */
@Slf4j
@Validated
@Controller
@RequiredArgsConstructor
public class QuestionController extends BaseController {

    private final IQuestionService questionService;

    private final IQuestionFileService questionFileService;
    private final IChapterQuestionService chapterQuestionService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "question")
    public String questionIndex(){
        return FebsUtil.view("question/question");
    }

    @GetMapping("question")
    @ResponseBody
    @RequiresPermissions("question:list")
    public FebsResponse getAllQuestions(Question question) {
        System.out.println();
        return new FebsResponse().success().data(questionService.getQuestionDetailList());
    }

    @GetMapping("question/list")
    @ResponseBody
    @RequiresPermissions("question:list")
    public FebsResponse questionList(QueryRequest request,Question question) {
//        System.out.println("测试");
//        System.out.println(questionService.getQuestionDetailList());
////        System.out.println(questionService.getQuestionDetailList(request));
//        System.out.println("结束");
       return new FebsResponse().success().data(getDataTable(questionService.getQuestionDetailList(request,question)));

//        return new FebsResponse().success().data(getDataTable(questionService.getQuestionDetailList(request,question)));
    }

    @ControllerEndpoint(operation = "新增Question", exceptionMessage = "新增Question失败")
    @PostMapping("question")
    @ResponseBody
    @RequiresPermissions("question:add")
    public FebsResponse addQuestion(@Valid Question question) throws FileNotFoundException {

        QuestionFile questionFile = new QuestionFile();
        MultipartFile file1 = question.getResultPic();
        List<MultipartFile > multipartFile = question.getTestPic1();
//        System.out.println("调试插入问题");
//        System.out.println(question);
//        System.out.println("调试插入问题");


//        System.out.println(file1 != null && !file1.isEmpty());
        if(question.getQuestionResultType().equals("0")) {
            if (file1 != null && !file1.isEmpty()) {
                try {
                    // 开始上传操作

                    String fileName = file1.getOriginalFilename();
                    String path = ResourceUtils.getURL("classpath:").getPath() + "static/";  //获取项目相对路径
                    String filePath = "images/" + getCurrentUser().getUserId() + "/" + new Date().getTime() + "/"+ "result/";
                    File destPath = new File(path + filePath);
                    if (!destPath.exists()) {
                        destPath.mkdirs();
                    }

                    File dest = new File(path + filePath, fileName);

                    // 上传成功
                    file1.transferTo(dest);
                    question.setQuestionResult(filePath + fileName);


                } catch (IOException e) {
                    // 上传失败
                    System.out.println(e.getMessage());
                    return new FebsResponse().fail();
                }
            }
        }
        User user = getCurrentUser();
        question.setQuestionCreateUser(user.getUserId());
//        System.out.println("11 2 2  2 2 2  ");
//        System.out.println(question );
//        System.out.println("11 2 2  2 2 2  ");
        this.questionService.createQuestion(question);
//        System.out.println("插入开始");
        ChapterQuestion chapterQuestion=new ChapterQuestion();
        chapterQuestion.setChapterId(question.getChapterId());
        chapterQuestion.setQuestionId(question.getQuestionId());
//        System.out.println(chapterQuestion);
        this.chapterQuestionService.createChapterQuestion(chapterQuestion);
        String str="/exercise/view/"+chapterQuestion.getChapterId();
//        System.out.println(str);
        this.questionService.updataMeau(chapterQuestion.getChapterId(),str);
//        System.out.println("插入完成");
//        System.out.println(question.getQuestionId());
//        System.out.println(question);
        if(question.getQuestionResultType().equals("0")) {
            String path = ResourceUtils.getURL("classpath:").getPath() + "static/";  //获取项目相对路径
            String filePath = "images/" + getCurrentUser().getUserId() + "/"+ new Date().getTime() + "/" + "test/";
            for (MultipartFile file : multipartFile) {
                if (file != null && !file.isEmpty()) {
                    try {
                        // 开始上传操作
                        String fileName = file.getOriginalFilename();


//                    + new Date().getTime() + "/"
                        File destPath = new File(path + filePath);
                        if (!destPath.exists()) {
                            destPath.mkdirs();
                        }

                        File dest = new File(path + filePath, fileName);

                        // 上传成功
                        file.transferTo(dest);
                        question.setQuestionResult(filePath + fileName);

                        questionFile.setFilePath(question.getQuestionResult());
                        questionFile.setFileName(fileName);
                        questionFile.setCreateTime(new Date());
//                    System.out.println(questionFile.createTime);
                        questionFile.setQuestionId(question.getQuestionId());
                        questionFile.setCreateUser(question.getQuestionCreateUser());
                        questionService.setQuestionFile(questionFile);
                    } catch (IOException e) {
                        // 上传失败
                        System.out.println(e.getMessage());
                        return new FebsResponse().fail();
                    }
                }
            }
        }
//        System.out.println(question);


        return new FebsResponse().success();

    }
//    @ControllerEndpoint(operation = "新增测试Question", exceptionMessage = "新增测试Question失败")
//    @PostMapping("question1")
//    @ResponseBody
//    @RequiresPermissions("question:add")
//    public FebsResponse addTestQuestion(@Valid Question question) {
//        System.out.println(question);
//        List<MultipartFile > multipartFile = question.getTestpic1();
//        for(MultipartFile file:multipartFile) {
//            if (file != null && !file.isEmpty()) {
//                try {
//                    // 开始上传操作
//                    String fileName = file.getOriginalFilename();
//                    String path = ResourceUtils.getURL("classpath:").getPath() + "static/";  //获取项目相对路径
//                    String filePath = "images/" + getCurrentUser().getUserId() + "/" + new Date().getTime() + "/";
//                    File destPath = new File(path + filePath);
//                    if (!destPath.exists()) {
//                        destPath.mkdirs();
//                    }
//
//                    File dest = new File(path + filePath, fileName);
//
//                    // 上传成功
//                    file.transferTo(dest);
//                    question.setQuestionResult(filePath + fileName);
//                } catch (IOException e) {
//                    // 上传失败
//                    System.out.println(e.getMessage());
//                    return new FebsResponse().fail();
//                }
//            }
//        }
//            User user = getCurrentUser();
//            question.setQuestionCreateUser(user.getUserId());
//            this.questionService.createQuestion(question);
//            return new FebsResponse().success();
//
//
//
//    }


    @ControllerEndpoint(operation = "删除Question", exceptionMessage = "删除Question失败")
    @GetMapping("question/delete/{questionIds}")
    @ResponseBody
    @RequiresPermissions("question:delete")
    public FebsResponse deleteQuestion(@NotBlank(message = "{required}") @PathVariable String questionIds) {
        this.questionService.deleteQuestion(questionIds);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改Question", exceptionMessage = "修改Question失败")
    @PostMapping("question/update")
    @ResponseBody
    @RequiresPermissions("question:update")
    public FebsResponse updateQuestion(Question question) throws FileNotFoundException {

//        System.out.println(question);
        QuestionFile questionFile = new QuestionFile();
        MultipartFile file1 = question.getResultPic();
        List<MultipartFile > multipartFile = question.getTestPic1();


        if (file1 != null && !file1.isEmpty()) {
            try {
                // 开始上传操作
                String fileName = file1.getOriginalFilename();
                String path = ResourceUtils.getURL("classpath:").getPath() + "static/";  //获取项目相对路径
                String filePath = "images/" +getCurrentUser().getUserId() + "/" + new Date().getTime() + "/" + "result/";
                File destPath = new File(path + filePath);
                if (!destPath.exists()) {
                    destPath.mkdirs();
                }

                File dest = new File(path + filePath , fileName);

                // 上传成功
                file1.transferTo(dest);
                question.setQuestionResult(filePath + fileName);


            } catch (IOException e) {
                // 上传失败
                System.out.println(e.getMessage());
                return new FebsResponse().fail();
            }
        }
        User user = getCurrentUser();
        question.setQuestionCreateUser(user.getUserId());
        this.questionService.updateQuestion(question);
//        System.out.println(question);
        if (question.getQuestionResultType().equals("0"))
        {
            String path = ResourceUtils.getURL("classpath:").getPath() + "static/";  //获取项目相对路径
            String filePath = "images/" + getCurrentUser().getUserId() + "/"+ new Date().getTime() + "/"+"test/" ;

              for(MultipartFile file:multipartFile) {
                if (file != null && !file.isEmpty()) {
                    try {
                        // 开始上传操作
                        String fileName = file.getOriginalFilename();


                        File destPath = new File(path + filePath);
                        if (!destPath.exists()) {
                            destPath.mkdirs();
                        }

                        File dest = new File(path + filePath , fileName);

                        // 上传成功
                        file.transferTo(dest);
                        question.setQuestionResult(filePath + fileName);

                        questionFile.setFilePath(question.getQuestionResult());
                        questionFile.setFileName(fileName);
                        questionFile.setCreateTime( new Date());
//                   System.out.println(questionFile.createTime);
                        questionFile.setQuestionId(question.getQuestionId());
                        questionFile.setCreateUser(question.getQuestionCreateUser());
                        questionService.setQuestionFile(questionFile);
                    } catch (IOException e) {
                        // 上传失败
                        System.out.println(e.getMessage());
                        return new FebsResponse().fail();
                    }
                }
            }
        }


//        System.out.println(question);
        //昨天写到上面那一句
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改Question", exceptionMessage = "导出Excel失败")
    @PostMapping("question/excel")
    @ResponseBody
    @RequiresPermissions("question:export")
    public void export(QueryRequest queryRequest, Question question, HttpServletResponse response) {
        List<Question> questions = this.questionService.findQuestions(queryRequest, question).getRecords();
        ExcelKit.$Export(Question.class, response).downXlsx(questions, false);
    }

    @ControllerEndpoint(operation = "根据chapterId获取所有题目信息", exceptionMessage = "获取题目信息失败")
    @GetMapping("/chapter/{chapterId}")
    @ResponseBody
    @RequiresPermissions("exercise:view")
    public FebsResponse chapterData(@PathVariable(name = "chapterId") Long chapterId) {
        List<Question> questionList = questionService.getQuestionDetailListByChapterId(chapterId);
        if (questionList.size() > 0){
            Question currentQuestion = questionList.get(0);
            QuestionFile questionFile = new QuestionFile();
            questionFile.setQuestionId(currentQuestion.getQuestionId());
            List<QuestionFile> questionFileList = this.questionFileService.findQuestionFiles(questionFile);
            currentQuestion.setTestPic(questionFileList);
        }
        return new FebsResponse().success().data(questionList);
    }
}
