package com.ven.controller;
import com.ven.anno.Log;
import com.ven.pojo.Emp;
import com.ven.pojo.PageBean;
import com.ven.pojo.Result;
import com.ven.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/emps")
public class EmpController {
    @Autowired
    private EmpService empService;

    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String name, Short gender,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
                       @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end) {
//        if(page == null) page = 1;
//        if(pageSize == null) pageSize = 10;
        log.info("分页查询，参数：{},{},{},{},{},{}", page, pageSize, name, gender, begin, end);
        PageBean pageBean =  empService.page(page,pageSize,name, gender, begin, end);
        return Result.success(pageBean);
    }
    @Log
    @DeleteMapping("/{ids}")
    public Result delete(@PathVariable List<Integer> ids) {
        log.info("批量删除操作，ids：{}", ids);
        empService.delete(ids);
        return Result.success();
    }

    //新增员工
    @Log
    @PostMapping
    public Result save(@RequestBody Emp emp) {
        log.info("新增员工emp:{}", emp);
        empService.save(emp);
        return Result.success();
    }

    /*修改员工*/
    //查询员工
    @GetMapping("/{id}")
    public Result getById(@PathVariable Integer id) {
        log.info("查询ID为：{}",id);
        Emp emp = empService.getById(id);
        return Result.success(emp);
    }
    //更新员工
    @Log
    @PutMapping
    public Result update(@RequestBody Emp emp) {
        log.info("更新员工信息：{}", emp);
        empService.update(emp);
        return Result.success();
    }
}

