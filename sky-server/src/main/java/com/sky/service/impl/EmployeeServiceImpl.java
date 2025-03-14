package com.sky.service.impl;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.util.StringUtil;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对,对前端传过来的密码进行加密
        String s = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!employee.getPassword().equals(s)) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    @Override
    public Result saveEmployee(EmployeeDTO employee) {

        Employee emp = BeanUtil.copyProperties(employee, Employee.class);
        emp.setPassword(MD5.create().digestHex(PasswordConstant.DEFAULT_PASSWORD));
        emp.setStatus(StatusConstant.ENABLE);
        employeeMapper.save(emp);
        return Result.success();
    }

    @Override
    public Result<PageResult> page(Integer page, Integer pageSize, String name) {
        //开始分页查询
        PageHelper.startPage(page, pageSize);
        Page<Employee> page1 =  employeeMapper.page(name);
        long total = page1.getTotal();
        List<Employee> result = page1.getResult();
        return Result.success(new PageResult(total,result));
    }

    @Override
    public Result modifyStatus(Integer status, Long id) {
        //修改员工账号状态
        Employee employee = new Employee();
        employee.setStatus(status);
        employee.setId(id);
        employeeMapper.update(employee);
        return Result.success();
    }

    @Override
    public Result updateById(EmployeeDTO employeeDTO) {
        Employee employee = BeanUtil.copyProperties(employeeDTO, Employee.class);
        employeeMapper.update(employee);
        return Result.success();
    }

    @Override
    public Employee queryById(Long id) {
        Employee  employee=employeeMapper.select(id);
        return employee;
    }

}
