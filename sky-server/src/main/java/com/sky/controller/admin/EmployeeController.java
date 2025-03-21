package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@Api(tags = "员工相关接口")
@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @ApiOperation("员工登录")
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);
        Employee employee = employeeService.login(employeeLoginDTO);
        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @ApiOperation("员工退出")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }


    @ApiOperation("添加员工")
    @PostMapping
    public Result saveEmployee(@RequestBody @Validated EmployeeDTO employee) {
            return  employeeService.saveEmployee(employee);
    }

    @ApiOperation("员工分页查询")
    @GetMapping("/page")
    public Result<PageResult> page( @RequestParam Integer page, @RequestParam Integer pageSize,@RequestParam(required = false,defaultValue = "") String name ){
        return employeeService.page(page,pageSize,name);
    }

    @ApiOperation("禁用或启用员工账号")
    @PostMapping("status/{status}")
    public Result modifyStatus(@PathVariable Integer status,Long id){
        return employeeService.modifyStatus(status,id);
    }
    @PutMapping
    @ApiOperation("修改员工信息")
    public Result updateById(@RequestBody @Validated EmployeeDTO employeeDTO) {
        return employeeService.updateById(employeeDTO);
    }

    @GetMapping("/{id}")
    public Result queryById(@PathVariable Long id) {
        Employee employee = employeeService.queryById(id);
        if (employee == null) {
            return Result.error("用户不存在");
        }
        return  Result.success(employee);
    }

}
