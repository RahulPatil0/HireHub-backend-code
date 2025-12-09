package com.hirehub.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dg7dfjx2q",
                "api_key", "775586247913692",      // CORRECT
                "api_secret", "3e6WEpsHt0bre_puMrN4FzCYn94", // CORRECT
                "secure", true
        ));
    }
}