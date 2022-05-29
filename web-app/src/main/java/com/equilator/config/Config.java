package com.equilator.config;

import com.equilator.models.UploadForm;
import com.equilator.services.FileService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
@Import({SecurityConfig.class, Beans.class, DatabaseConfig.class})
public class Config {


    @Bean
    public FileService saveResult(){
        return new FileService();
    }

    @Bean
    public UploadForm uploadForm(){
        return new UploadForm();
    }

    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }

    @Bean
    HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    @Bean
    public ClassLoaderTemplateResolver adminClassLoaderTemplateResolver() {
        ClassLoaderTemplateResolver classLoaderTemplateResolver = new ClassLoaderTemplateResolver();
        classLoaderTemplateResolver.setPrefix("templates/admin/");
        classLoaderTemplateResolver.setSuffix(".html");
        classLoaderTemplateResolver.setTemplateMode(TemplateMode.HTML);
        classLoaderTemplateResolver.setCharacterEncoding("UTF-8");
        classLoaderTemplateResolver.setOrder(0);
        classLoaderTemplateResolver.setCheckExistence(true);

        return classLoaderTemplateResolver;
    }

    @Bean
    public ClassLoaderTemplateResolver userClassLoaderTemplateResolver() {
        ClassLoaderTemplateResolver classLoaderTemplateResolver = new ClassLoaderTemplateResolver();
        classLoaderTemplateResolver.setPrefix("templates/user/");
        classLoaderTemplateResolver.setSuffix(".html");
        classLoaderTemplateResolver.setTemplateMode(TemplateMode.HTML);
        classLoaderTemplateResolver.setCharacterEncoding("UTF-8");
        classLoaderTemplateResolver.setOrder(0);
        classLoaderTemplateResolver.setCheckExistence(true);

        return classLoaderTemplateResolver;
    }

    @Bean
    public ClassLoaderTemplateResolver calculatorClassLoaderTemplateResolver() {
        ClassLoaderTemplateResolver classLoaderTemplateResolver = new ClassLoaderTemplateResolver();
        classLoaderTemplateResolver.setPrefix("templates/calculator/");
        classLoaderTemplateResolver.setSuffix(".html");
        classLoaderTemplateResolver.setTemplateMode(TemplateMode.HTML);
        classLoaderTemplateResolver.setCharacterEncoding("UTF-8");
        classLoaderTemplateResolver.setOrder(0);
        classLoaderTemplateResolver.setCheckExistence(true);

        return classLoaderTemplateResolver;
    }

     @Bean
    public CommonsMultipartResolver multipartResolver(){
        return new CommonsMultipartResolver();
     }


}