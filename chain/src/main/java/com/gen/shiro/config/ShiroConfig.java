package com.gen.shiro.config;

import com.gen.filter.JwtFilter;
import com.gen.shiro.realm.JwtRealm;
import com.gen.shiro.realm.MultiRealmAuthenticator;
import com.gen.shiro.realm.ShiroRealm;
import com.gen.utils.JwtCredentialsMatcher;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 1、我们这个ShiroConfig禁用了session
 * 2、添加自定义的jwtFilter过滤器，用来拦截自定义的JWT token
 * 3、使用自定义的MultiRealmAuthenticator多Realm认证器，解决认证异常无法正常返回的问题(这我不太懂，抱歉)
 * 4、JwtRealm+ShiroRealm的双realm，JwtRealm专门处理 JWT token验证身份的请求
 * <p>
 * shiroConfig主要是配置shiro的一些基本策略，让shiro能被用起来。例如我们最基本的需要 shiroFilter、SecurityManager、自定义realm
 */
@Configuration
public class ShiroConfig {
    /**
     * 交由 Spring 来自动管理 Shiro-Bean 的生命周期
     */
    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 为 Spring-Bean 开启对 Shiro 注解的支持
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator app = new DefaultAdvisorAutoProxyCreator();
        app.setProxyTargetClass(true);
        return app;

    }

    /**
     * 不向 Spring容器中注册 JwtFilter Bean，防止 Spring 将 JwtFilter 注册为全局过滤器
     * 全局过滤器会对所有请求进行拦截，而本例中只需要拦截除 /login 和 /logout 外的请求
     * 另一种简单做法是：直接去掉 jwtFilter()上的 @Bean 注解
     */
    @Bean
    public FilterRegistrationBean<Filter> registration(JwtFilter filter) {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>(filter);
        registration.setEnabled(false);
        return registration;
    }

    // @Bean
    public JwtFilter jwtFilter() {
        //过滤器如果加了@Compoent就没必要用这个方法了
        return new JwtFilter();
    }

    /**
     * 配置访问资源需要的权限
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //给工厂bean设置web安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/authorized");
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");

        // 添加 jwt 专用过滤器，拦截除 /login 和 /logout 外的请求
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("jwtFilter", jwtFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        //配置系统受限资源以及公共资源
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/user/login", "anon");
        filterChainDefinitionMap.put("/user/register", "anon");
        filterChainDefinitionMap.put("/common/kaptcha", "anon");
        filterChainDefinitionMap.put("/logout", "logout"); // 退出登录

        //knife4j
        filterChainDefinitionMap.put("/doc.html", "anon");
        filterChainDefinitionMap.put("/webjars/**/**", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/v2/api-docs", "anon");
        filterChainDefinitionMap.put("/v2/api-docs-ext", "anon");

        filterChainDefinitionMap.put("/**", "jwtFilter,authc"); // 需登录才能访问
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 配置 ModularRealmAuthenticator
     * SecurityManager得到token信息后，通过调用authenticator.authenticate(token)方法，
     * 把身份验证委托给内置的Authenticator的实例进行验证。authenticator通常是ModularRealmAuthenticator
     * 如果配置了多个Realm，ModularRealmAuthenticator会根据配置的AuthenticationStrategy（身份验证策略）进行多Realm认证过程。
     */
    @Bean
    public ModularRealmAuthenticator authenticator() {
        ModularRealmAuthenticator authenticator = new MultiRealmAuthenticator();
        // 设置多 Realm 的认证策略，默认为 AtLeastOneSuccessfulStrategy
        authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
        return authenticator;
    }

    /**
     * 禁用session, 不保存用户登录状态。保证每次请求都重新认证
     * 评估Shiro是否可以使用Subject的Session来持久化Subject的内部状态。
     * 常用的Shiro实现策略是使用Subject的Session来持久化Subject的身份和身份验证状态(例如登录后)，
     * 这样信息就不需要为任何进一步的请求/调用传递了。
     * 这有效地允许一个sessionId用于任何请求或调用，作为Shiro需要的唯一“指针”，
     * Shiro可以基于引用的session重新创建Subject实例。
     */
    @Bean
    protected SessionStorageEvaluator sessionStorageEvaluator() {
        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }

    /**
     * JwtRealm 配置，需实现 Realm 接口，专门处理 JWT token验证身份的请求
     */
    @Bean
    JwtRealm jwtRealm() {
        JwtRealm jwtRealm = new JwtRealm();
        // 设置加密算法
        CredentialsMatcher credentialsMatcher = new JwtCredentialsMatcher();
        // 设置加密次数
        jwtRealm.setCredentialsMatcher(credentialsMatcher);
        return jwtRealm;
    }

    /**
     * ShiroRealm 配置，需实现 Realm 接口
     */
    @Bean
    ShiroRealm shiroRealm() { //这里其实是模拟的数据库的类，但是也继承了AuthorizingRealm
        ShiroRealm shiroRealm = new ShiroRealm();
        // 设置加密算法
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher("MD5");
        // 设置加密次数
        credentialsMatcher.setHashIterations(3);
        shiroRealm.setCredentialsMatcher(credentialsMatcher);
        return shiroRealm;
    }

    /**
     * 配置 DefaultWebSecurityManager
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        // 1.Authenticator
        securityManager.setAuthenticator(authenticator());

        // 2.Realm
        List<Realm> realms = new ArrayList<Realm>(16);
        realms.add(jwtRealm());
        realms.add(shiroRealm());
        securityManager.setRealms(realms); // 配置多个realm

        // 3.关闭shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator());
        securityManager.setSubjectDAO(subjectDAO);

        return securityManager;
    }

    //cookie属性设置
    public SimpleCookie rememberMeCookie() {
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        //设置跨域
//        cookie.setDomain(domain);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(30 * 24 * 60 * 60);
        return cookie;
    }

    //创建Shiro的cookie管理对象
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey("1234567890987654".getBytes());
        return cookieRememberMeManager;
    }

    @Bean
    public Authorizer authorizer() {
        //这里是个坑，如果没有这个bean，启动会报错，所以得加上
        return new ModularRealmAuthorizer();
    }
}
