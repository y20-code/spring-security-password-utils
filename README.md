# spring-security-password-utils
一个加密技术

## spring-security-password-utils
一个加密技术、JWT、Spring Security 的安全策略
### 一个加密技术 1.先添加security到pom.xml文件
```xml
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```
### 2.配置一个密码编码器
```java
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
```
### 3.创建一个实体类 
代码省略，不会可以参考User
### 4.测试
```java
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

### JWT令牌 1.先导入
```xml
<dependency>
            <groupId>com.auth0</groupId>
            <artifactId>java-jwt</artifactId>
            <version>4.4.0</version>
</dependency>
```
### 2.遍写jwt令牌工具类
```java
    @Value("${jwt.secret}") //设置密钥
    private String secret;

    @Value("${jwt.expiration}")  //设置多久时间过期
    private Long expiration;

    public String  generateToken(String username) {
        return JWT.create()  //创建一个新的 JWT 构建器
                .withSubject(username) // 设置主题（用户标识）
                .withIssuedAt(new Date()) // 发行时间
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration)) // 过期时间
                .sign(Algorithm.HMAC256(secret)); // 使用 HMAC256 算法签名
    }

    // 验证并解析 JWT 令牌
    public DecodedJWT verifyToken(String token) throws JWTVerificationException {
        return JWT.require(Algorithm.HMAC256(secret))
                .build()
                .verify(token); // 验证令牌并返回解码后的 JWT
    }

    // 从令牌中提取用户名
    public String getUsernameFromToken(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);//解码令牌，不验证签名，仅解析内容
            String username = jwt.getSubject();//获取 sub 字段的值，即用户名。
            return username;
        } catch (Exception e) {
            return null;
        }
    }
```
### 3.验证令牌是否能通过
```java
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7); // 移除 "Bearer " 前缀
            try {
                String username = jwtUtil.getUsernameFromToken(token); // 使用 token 提取用户名
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if (jwtUtil.verifyToken(token) != null) { // 使用 token 验证
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token: " + e.getMessage());
                return;
            }
        }
        chain.doFilter(request, response);
    }
```

### 4.Spring Security 的安全策略
```java
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 开发环境禁用 CSRF
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/admin/login").permitAll() // 允许匿名访问登录端点
                                .anyRequest().authenticated() // 其他请求需要认证
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // 添加 JWT 过滤器
        return http.build();
    }
```



## spring-boot-starter-validation
接口的参数合法校验和全局异常处理
### 1.先导入validation、web到pom.xml文件
```xml
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

#### 2.2 `@NotEmpty`
- **作用**: 确保字段或参数不为 `null`。
- **示例**:
  ```java
  @NotNull(message = "用户名不能为空")
  private String a;

```

```java
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


