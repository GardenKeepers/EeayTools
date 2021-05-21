package com.gardenkeepers.security.config;

import com.gardenkeepers.security.jwt.JwtTokenFilterConfigurer;
import com.gardenkeepers.security.jwt.JwtTokenProvider;
import com.gardenkeepers.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailsService).passwordEncoder(new PasswordEncoder() {
//			@Override
//			public String encode(CharSequence charSequence) {
//				return charSequence.toString();
//			}
//
//			@Override
//			public boolean matches(CharSequence charSequence, String s) {
//				return s.equals(charSequence.toString());
//			}
//		});
//	}

    /**
     * https://blog.csdn.net/yuanlaijike/article/details/84638745?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522159383475219724843336991%2522%252C%2522scm%2522%253A%252220140713.130102334.pc%255Fall.%2522%257D&request_id=159383475219724843336991&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~first_rank_ecpm_v3~pc_rank_v3-1-84638745.pc_ecpm_v3_pc_rank_v3&utm_term=%E5%A6%82%E6%9E%9C%E6%9C%89%E5%85%81%E8%AE%B8%E5%8C%BF%E5%90%8D%E7%9A%84url%EF%BC%8C%E5%A1%AB%E5%9C%A8%E4%B8%8B%E9%9D%A2
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                // 如果有允许匿名的url，填在下面
                .antMatchers("/", "/home", "/jianjie", "/xinwen", "/wenti", "/fuwu", "/wangluo",
                        "/lianxi", "/zhishi", "/ditu", "/heating", "/sstj", "/tpll", "/device/cropSpecies/list", "/device/lure/list", "/img/list", "/realtimedata/outexcel", "/showImg", "/realtimedata/getdata").permitAll()
                .antMatchers("/user/signin").permitAll()
                .antMatchers("/user/signup").permitAll()//
                .antMatchers("/h2-console/**/**").permitAll()
                .anyRequest().authenticated();
        // 设置登陆页
//				.formLogin().loginPage("/login")
//				.loginProcessingUrl("/authentication/form")// 自定义登录路径
        // 设置登陆成功地址---一定要使用这个,那个有坑,坑我两个小时
//				.successForwardUrl("/login-allot")
        // 设置登入失败页面
//				.failureUrl("/login/error").permitAll()
        // 自定义登陆用户名和密码参数，默认为username和password
//                .usernameParameter("username")
//                .passwordParameter("password")
//				.and()
//				.logout().logoutUrl("/logout").logoutSuccessUrl("/home").deleteCookies("JSESSIONID");

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.apply(new JwtTokenFilterConfigurer(jwtTokenProvider));

        // 关闭CSRF跨域
        http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 设置拦截忽略文件夹，可以对静态资源放行
        web.ignoring().antMatchers("/css/**", "/js/**", "/static/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

