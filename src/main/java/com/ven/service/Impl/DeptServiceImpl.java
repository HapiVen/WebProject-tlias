package com.ven.service.Impl;
import com.ven.mapper.DeptMapper;
import com.ven.mapper.EmpMapper;
import com.ven.pojo.Dept;
import com.ven.pojo.DeptLog;
import com.ven.service.DeptLogService;
import com.ven.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private DeptLogService deptLogService;

    /*查询全部部门*/
    @Override
    public List<Dept> list() {
        return deptMapper.list();
    }

    /*根据id删除部门*/
    @Override
    @Transactional(rollbackFor=Exception.class)  //当前方法交给spring进行事务处理
    public void delete(Integer id) {
        try {
            deptMapper.deleteById(id); //根据ID删除部门
            empMapper.deleteByDeptId(id); //根据部门ID删除该部门下的员工
        } finally {
            //不论是否有异常，最终都要执行的代码：记录日志
            DeptLog deptLog = new DeptLog();
            deptLog.setCreateTime(LocalDateTime.now());
            deptLog.setDescription("执行了解散部门的操作，此时解散的是"+id+"号部门");
            //调用其他业务类中的方法
            deptLogService.insert(deptLog);
        }
    }
    /*新增部门*/
    @Override
    public void add(Dept dept) {
        //需要补全时间属性
        dept.setCreateTime(LocalDateTime.now());
        dept.setUpdateTime(LocalDateTime.now());

        deptMapper.insert(dept);
    }
    /*修改部门*/
    @Override
    public Dept selectByDeptId(Integer id) {
        Dept dept = deptMapper.selectByDeptId(id);
        return dept;
    }
    @Override
    public void update(Dept dept) {
        dept.setUpdateTime(LocalDateTime.now());
        deptMapper.update(dept);
    }
}
