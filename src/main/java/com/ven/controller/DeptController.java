package com.ven.controller;

import com.ven.anno.Log;
import com.ven.pojo.Dept;
import com.ven.pojo.Result;
import com.ven.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/*部门管理*/
@Slf4j
@RestController
@RequestMapping("/depts")
public class DeptController {
    //private static Logger log = LoggerFactory.getLogger(DeptController.class); //日志记录对象
    //@Slf4j代替

    @Autowired
    private DeptService deptService;   //面向接口编程

    //查询部门数据
    //@RequestMapping(value = "/depts", method = RequestMethod.GET)//指定当前请求接口的路径
    //下面代替上面的
    @GetMapping
    public Result list() {
        log.info("查询全部部门数据");
        //调用service查询部门数据
        List<Dept> deptList = deptService.list();

        return Result.success(deptList);
    }

    //删除部门
    //通过 @PathVariable 可以将 URL 中占位符参数绑定到控制器处理方法的入参中:URL 中的 {xxx} 占位符可以通过
    //@PathVariable(“xxx”) 绑定到操作方法的入参中。
    @Log
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("根据id删除部门：{}",id);
        //调用service删除部门
        deptService.delete(id); //根据ID删除部门数据
        return Result.success();
    }

    /*新增部门*/
    @Log
    @PostMapping
    //@RequestBody  把前端传递的json数据填充到实体类中
    public Result add(@RequestBody Dept dept) {
        log.info("新增部门:{}", dept);
        deptService.add(dept);
        return Result.success();
    }

    /*修改部门*/
    @GetMapping("/{id}")
    public Result selectByDeptId(@PathVariable Integer id) {
        log.info("根据id查询部门：{}", id);
        //调用Service层
        Dept dept = deptService.selectByDeptId(id);
        return Result.success(dept);
    }
    @PutMapping
    public Result update(@RequestBody Dept dept) {
        log.info("修改部门:{}", dept);
        deptService.update(dept);
        return Result.success();
    }
}
