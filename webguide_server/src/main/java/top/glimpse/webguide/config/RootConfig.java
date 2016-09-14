package top.glimpse.webguide.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by joyce on 16-5-11.
 */
@Configuration
@ComponentScan(basePackages = {"top.glimpse.webguide"},
    excludeFilters = {
            @Filter(type = FilterType.ANNOTATION,value = EnableWebMvc.class)
    })
public class RootConfig {
}
