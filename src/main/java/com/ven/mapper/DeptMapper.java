package com.ven.mapper;
import com.ven.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DeptMapper {
    /*查询全部部门*/
    @Select("select * from dept")
    List<Dept> list();
    /*根据id删除部门*/
    @Delete("delete from dept where id = #{id}")
    void deleteById(Integer id);
    /*新增部门*/
    @Insert("insert into dept (name, create_time,update_time) VALUES (#{name},#{createTime},#{updateTime})")
    void insert(Dept dept);
    /*修改部门*/
    @Select("select *from dept where id = #{id}")
    Dept selectByDeptId(Integer id);
    @Update("update dept set name = #{name}, update_time = #{updateTime} where id = #{id}")
    void update(Dept dept);
}
