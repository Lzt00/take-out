package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;
import com.sky.result.Result;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    Result saveEmployee(EmployeeDTO employee);

    Result<PageResult> page(Integer page, Integer pageSize, String name);

    Result modifyStatus(Integer status, Long id);

    Result updateById(EmployeeDTO employeeDTO);

    Employee queryById(Long id);

}
