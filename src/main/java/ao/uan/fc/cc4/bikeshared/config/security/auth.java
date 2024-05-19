/*package ao.uan.fc.cc4.bikeshared.config.security;

import ao.uan.fc.cc4.bikeshared.config.security.token.FilterToken;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

public class auth  {

    @Bean
    public FilterRegistrationBean<FilterToken> jwtFilter() {
        FilterRegistrationBean<FilterToken> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new FilterToken());
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

}
*/