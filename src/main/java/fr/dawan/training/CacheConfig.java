package fr.dawan.training;

import org.springframework.cache.annotation.AnnotationCacheOperationSource;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheInterceptor;
import org.springframework.cache.interceptor.CacheOperationSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import net.sf.ehcache.CacheManager;

//@Configuration
//@EnableCaching
//@Profile("test")
public class CacheConfig {

//	@Bean
//	public CacheManager cacheManager() {
//		return new CacheManager();
//	}
//	
//	@Bean
//	public CacheOperationSource cacheOperationSource() {
//		return new AnnotationCacheOperationSource();
//	}
//	
//	@Bean
//	public CacheInterceptor cacheInterceptor() {
//		CacheInterceptor intercep = new CacheInterceptor();
//		intercep.setCacheOperationSources(cacheOperationSource());
//		return intercep;
//	}
}
