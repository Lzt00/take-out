package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags="分类相关接口")
@RestController
@RequestMapping("/admin/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @ApiOperation("分页查询分类")
    @RequestMapping("/page")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO){
       PageResult  pageResult=categoryService.page(categoryPageQueryDTO);
       return Result.success(pageResult);
    }


    @ApiOperation("新增分类")
    @PostMapping
    public Result addCategory ( @RequestBody CategoryDTO categoryDTO){
        categoryService.addCateggory(categoryDTO);
        return  Result.success();
    }

    @ApiOperation("修改分类")
    @PutMapping
    public Result modifyCategory ( @RequestBody CategoryDTO categoryDTO){
        categoryService.modifyCategory(categoryDTO);
        return  Result.success();
    }

    @ApiOperation("修改分类状态")
    @PostMapping("/status/{status}")
    public Result modifyStatus(@PathVariable Integer status,Long id){
        categoryService.modifyStatus(status,id);
        return  Result.success();
    }

    @ApiOperation("删除分类")
    @DeleteMapping
    public Result deleteCategory(Long id){
        categoryService.deleteCategory(id);
        return  Result.success();
    }


    @ApiOperation("根据分类查询菜品")
    @GetMapping("/list")
    public Result<List<Category>> queryByType(String type){
        List<Category> categories = categoryService.queryByType(type);
        return Result.success(categories);
    }

}
