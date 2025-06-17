# spring-security-password-utils
一个加密技术

## spring-security-password-utils
一个加密技术
### 1.先添加security到pom.xml文件
```
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```
### 2.配置一个密码编码器
```
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
```
### 3.创建一个实体类 
代码省略，不会可以参考User
### 4.测试
```
@Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @Test
    public void testpassword() {
        // 准备测试数据
        User user = new User();
        String rawPassword = "Password123!";
        user.setUsername("testuser");
        user.setId("1");

        //使用 encode 生成加密密码，赋值给 encode。
        String encode = passwordEncoder.encode(rawPassword);

        System.out.println(encode);
        System.out.println(rawPassword);

        //现在对加密密码进行配对
        //用 matches 验证正确密码和错误密码。
        //默认相等返回ture，
        if(passwordEncoder.matches(rawPassword,encode)){
            System.out.println("密码一致");
        };
    }
```

## spring-boot-starter-validation
接口的参数合法校验和全局异常处理
### 1.先导入validation、web到pom.xml文件
```
//作用是参数合法校验
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
//作用是全局异常处理
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```
### 2.validation验证的用法
#### 2.1 `@NotNull`
- **作用**: 确保字段或参数不为 `null`。
- **示例**:
  ```java
  @NotNull(message = "用户名不能为空")
  private String a;

```
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
```

