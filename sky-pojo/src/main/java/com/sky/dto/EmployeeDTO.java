package com.sky.dto;

import lombok.Data;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class EmployeeDTO implements Serializable {

    private Long id;


    @NotBlank
    @Size(min = 3, max = 10)
    private String username;

    @NotBlank
    @Size(min = 1, max = 10)
    private String name;

    @NotBlank
    private String phone;

    @NotBlank
    private String sex;

    private String idNumber;

}
