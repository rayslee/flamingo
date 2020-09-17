package org.ray.flamingo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * Customized property file which used to guide the behavior of application
 * without touching source code.
 *
 * @author Ray LEE
 * @since 1.0
 */
@Component
@ConfigurationProperties(prefix = "flamingo.page")
@Validated
public class PageProps {
	
	@Min(value = 1)
	@Max(value = 20)
	private int size = 2;
	
	@Min(value = 10)
	@Max(value = 200)
	private int loadSize = 10;
	
	@Min(value = 10)
	@Max(value = 200)
	private int cacheSize = 100;
	
	/**
	 * Get the number of items should display in single view page 
	 * 
	 * @return page size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Get the number of items should load from DB one time which may be 
	 * used for screening
	 * 
	 * @return loading size
	 */
	public int getLoadSize() {
		return loadSize;
	}
	
	/**
	 * Get the number of items should be cached which will avoid repeatedly 
	 * accessing DB
	 * 
	 * @return cache size
	 */
	public int getCacheSize() {
		return cacheSize;
	}
	
}
