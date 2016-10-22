package edu.zh.filters;

import javax.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

//用于处理编码问题  
@Configuration
public class CharacterFilter{
	
    @Bean  
    public Filter characterEncodingFilter() {  
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();  
        characterEncodingFilter.setEncoding("UTF-8");  
        characterEncodingFilter.setForceEncoding(true);  
        return characterEncodingFilter;
    }
}
