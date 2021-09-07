package cc.mrbird.febs.exercise.controller;

import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.exercise.entity.Question;
import cc.mrbird.febs.exercise.service.IQuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
@Slf4j
@Validated
@Controller("exerciseView")
@RequestMapping(FebsConstant.VIEW_PREFIX + "exercise")
@RequiredArgsConstructor
public class ViewController {

    private final IQuestionService questionService;

    @GetMapping("/graphprocess/index")
    @RequiresPermissions("graphprocess:view")
    public String graphprocessView() {
        return FebsUtil.view("exercise/graphprocess/index");
    }

    @GetMapping("/manager/index")
    @RequiresPermissions("question:list")
    public String addView() {
        return FebsUtil.view("exercise/manager/index");
    }

    @GetMapping("/process/index")
    @RequiresPermissions("exercise:process")
    public String processView() {
        return FebsUtil.view("exercise/process/index");
    }

    @GetMapping("/view/{id}")
    @RequiresPermissions("exercise:view")
    public String exerciseView(@PathVariable(name = "id") String id) {
        System.out.println(id);
        return FebsUtil.view("exercise/graphprocess/index");
    }
}
