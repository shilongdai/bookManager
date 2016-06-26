package net.viperfish.bookManager;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.context.ServletContextAware;

@Configuration
@EnableAsync(proxyTargetClass = true, order = 1)
@EnableTransactionManagement(proxyTargetClass = true, order = Ordered.LOWEST_PRECEDENCE)
@EnableScheduling
@EnableJpaRepositories(basePackages = {
		"net.viperfish.bookManager.core" }, entityManagerFactoryRef = "entityManagerFactoryBean", transactionManagerRef = "jpaTransactionManager")
@ComponentScan(basePackages = "net.viperfish.bookManager", excludeFilters = @ComponentScan.Filter(Controller.class))
@Import(SecurityConfiguration.class)
public class RootApplicationContextConfig implements AsyncConfigurer, SchedulingConfigurer, ServletContextAware {

	private Logger log = LogManager.getLogger();
	private ServletContext context;

	@Bean
	public ThreadPoolTaskScheduler taskScheduler() {
		String levelString = context.getInitParameter("threadNumber");
		if (levelString == null) {
			levelString = "4";
		}
		int level = Integer.parseInt(levelString);
		log.info("Creating thread pool with " + level + " threads");
		ThreadPoolTaskScheduler exec = new ThreadPoolTaskScheduler();
		exec.setPoolSize(level);
		exec.setThreadNamePrefix("transaction");
		exec.setAwaitTerminationSeconds(60);
		exec.setWaitForTasksToCompleteOnShutdown(true);
		exec.setRejectedExecutionHandler(new RejectedExecutionHandler() {

			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
				StringBuilder errorBuilder = new StringBuilder("Task Rejected");
				log.error(errorBuilder.toString());
			}
		});
		return exec;
	}

	@Bean
	public LocalValidatorFactoryBean localValidatorFactoryBean() throws ClassNotFoundException {
		LocalValidatorFactoryBean result = new LocalValidatorFactoryBean();
		result.setProviderClass(Class.forName("org.hibernate.validator.HibernateValidator"));
		result.setValidationMessageSource(this.messageSource());
		return result;
	}

	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() throws ClassNotFoundException {
		MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
		processor.setValidator(this.localValidatorFactoryBean());
		return processor;
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		TaskScheduler scheduler = this.taskScheduler();
		taskRegistrar.setTaskScheduler(scheduler);
	}

	@Override
	public Executor getAsyncExecutor() {
		Executor exec = this.taskScheduler();
		log.info(exec + " ready for use");
		return exec;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new AsyncUncaughtExceptionHandler() {

			@Override
			public void handleUncaughtException(Throwable ex, Method method, Object... params) {
				StringBuilder errorBuilder = new StringBuilder("Async execution error on method:")
						.append(method.toString()).append(" with parameters:").append(Arrays.toString(params));
				log.error(errorBuilder.toString());
			}
		};
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.context = servletContext;

	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setCacheSeconds(-1);
		messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
		messageSource.setBasenames("WEB-INF/i18n/messages", "WEB-INF/i18n/errors");
		return messageSource;
	}

	@Bean
	public DataSource bookManagerDataSource() {
		JndiDataSourceLookup lookup = new JndiDataSourceLookup();
		return lookup.getDataSource("jdbc/bookManager");
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
		Map<String, Object> properties = new Hashtable<>();
		properties.put("javax.persistence.schema-generation.database.action", "none");
		properties.put("hibernate.connection.characterEncoding", "utf8");
		properties.put("hibernate.connection.useUnicode", "true");
		properties.put("hibernate.connection.charSet", "utf8");
		properties.put("hibernate.search.default.directory_provider", "filesystem");
		properties.put("hibernate.search.default.indexBase", context.getInitParameter("indexDir"));

		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabasePlatform("org.hibernate.dialect.MySQL5InnoDBDialect");

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(adapter);
		factory.setDataSource(this.bookManagerDataSource());
		factory.setPackagesToScan("net.viperfish.bookManager");
		factory.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
		factory.setValidationMode(ValidationMode.NONE);
		factory.setJpaPropertyMap(properties);
		return factory;
	}

	@Bean
	public PlatformTransactionManager jpaTransactionManager() {
		return new JpaTransactionManager(this.entityManagerFactoryBean().getObject());
	}

}
