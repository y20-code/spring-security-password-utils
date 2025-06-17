package com.yls.springbootstartervalidation.pojo;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class User {

    @NotNull(message = "用户名不能为空")
    private String a;

    @NotEmpty(message = "密码不能为空")
    private String b;

    @NotBlank(message = "邮箱不能为空或仅含空格")
    private String c;

    @Size(min = 5, max = 16, message = "用户名长度必须在5-16之间")
    private String d;

    @Email(message = "邮箱格式无效")
    private String e;

    @Min(value = 18, message = "年龄不能小于18")
    @Max(value = 100, message = "年龄不能大于100")
    private int f;

    @Positive(message = "ID必须为正数")
    private long g;

    @Past(message = "出生日期必须在过去")
    private String h;


    @Pattern(regexp = "^\\S{5,16}$", message = "必须是5-16个非空白字符")
    private String i;

    @AssertTrue(message = "必须同意用户协议")
    private boolean j;

    @AssertTrue(message = "必须同意用户协议")
    private boolean k;

}
