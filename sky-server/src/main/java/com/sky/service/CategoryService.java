package com.sky.service;


import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {

    PageResult page(CategoryPageQueryDTO categoryPageQueryDTO);

    void addCateggory(CategoryDTO categoryDTO);

    void modifyCategory(CategoryDTO categoryDTO);

    void modifyStatus(Integer status, Long id);

    void deleteCategory(Long id);

    List<Category> queryByType(String type);
}
