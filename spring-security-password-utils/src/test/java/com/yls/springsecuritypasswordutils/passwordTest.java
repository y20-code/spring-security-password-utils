package com.yls.springsecuritypasswordutils;


import com.yls.springsecuritypasswordutils.config.SecurityConfig;
import com.yls.springsecuritypasswordutils.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootTest(classes = SecurityConfig.class)
public class passwordTest {

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
}
