package fr.dawan.training.interceptors;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.interceptor.CacheInterceptor;

@SuppressWarnings("serial")
public class MyCacheInterceptor extends CacheInterceptor {

	private static final Logger ROOT_LOGGER = LoggerFactory.getLogger(MyCacheInterceptor.class);
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		ROOT_LOGGER.info("MyCacheInterceptor : " + invocation.toString());
		return super.invoke(invocation);
	}

	
	
}
