package org.deslre.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    private final String imagePath = "E:\\nuxt-blog-net\\deslre-blog\\src\\main\\resources\\static\\data\\data.assets";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + imagePath + "\\"); // 注意末尾加上 "\\"
    }
}
