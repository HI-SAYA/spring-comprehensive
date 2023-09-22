package com.ohgiraffers.comprehensive.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /* WebMvcConfigurer : WebMvc와 관련 된 설정을 할 수 있는 메소드가 default 메소드로 선언 된 인터페이스 */

    /* addResourceHandlers : 정적 리소스와 관련하여 static 경로는 기본으로 설정 되어 있으므로 추가적인 정적 리소스 경로를
     * 설정할 때 사용한다. */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("classpath:/upload/");
    }
}