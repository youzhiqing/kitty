package com.cxytiandi.kitty.zuul;

import com.cxytiandi.kitty.gateway.zuul.KittyRibbonRoutingFilter;
import com.dianping.cat.servlet.CatFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.ribbon.support.RibbonRequestCustomizer;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonCommandFactory;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonRoutingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

/**
 * Web自动配置
 *
 * @作者 尹吉欢
 * @个人微信 jihuan900
 * @微信公众号 猿天地
 * @GitHub https://github.com/yinjihuan
 * @作者介绍 http://cxytiandi.com/about
 * @时间 2020-02-16 21:53
 */
@Configuration
public class WebAutoConfiguration {

    @Autowired(required = false)
    private List<RibbonRequestCustomizer> requestCustomizers = Collections.emptyList();

    /**
     * 配置Cat Filter
     * @return
     */
    @Bean
    public FilterRegistrationBean catFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        CatFilter filter = new CatFilter();
        registration.setFilter(filter);
        registration.addUrlPatterns("/*");
        registration.setName("cat-filter");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public KittyRibbonRoutingFilter kittyRibbonRoutingFilter(ProxyRequestHelper helper,
                                                             RibbonCommandFactory<?> ribbonCommandFactory) {
        return new KittyRibbonRoutingFilter(helper, ribbonCommandFactory,
                this.requestCustomizers);
    }

    @Bean
    public HttpHeaderFilter httpHeaderFilter() {
        return new HttpHeaderFilter();
    }

}