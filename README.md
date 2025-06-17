# spring-security-password-utils
一个加密技术

## 1.先添加security到pom.xml文件
```
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```
## 2.配置一个密码编码器
```
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
```
## 3.创建一个实体类 
代码省略，不会可以参考User
## 4.测试
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
