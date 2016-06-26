package net.viperfish.bookManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.support.DomainClassConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.RequestToViewNameTranslator;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.DefaultRequestToViewNameTranslator;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "net.viperfish.bookManager", useDefaultFilters = false, includeFilters = @ComponentScan.Filter(Controller.class))
public class ServletApplicationContextConfig extends WebMvcConfigurerAdapter {

	private org.apache.logging.log4j.Logger log = LogManager.getLogger();

	@Autowired
	private SpringValidatorAdapter validator;

	@Autowired
	private ApplicationContext context;

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	@Bean
	public RequestToViewNameTranslator viewNameTranslator() {
		return new DefaultRequestToViewNameTranslator();
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		sdf.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
		binder.setValidator(this.validator);
	}

	@Override
	public Validator getValidator() {
		return this.validator;
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		if (!(registry instanceof FormattingConversionService)) {
			log.warn("Unable to register Spring Data Jpa converter");
			return;
		}

		DomainClassConverter<FormattingConversionService> converter = new DomainClassConverter<FormattingConversionService>(
				(FormattingConversionService) registry);
		converter.setApplicationContext(this.context);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		Sort defaultSort = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));
		Pageable defaultPage = new PageRequest(0, 10, defaultSort);

		SortHandlerMethodArgumentResolver sortHandlerMethodArgumentResolver = new SortHandlerMethodArgumentResolver();
		sortHandlerMethodArgumentResolver.setSortParameter("sort");
		sortHandlerMethodArgumentResolver.setFallbackSort(defaultSort);

		PageableHandlerMethodArgumentResolver pageableResolver = new PageableHandlerMethodArgumentResolver();
		pageableResolver.setMaxPageSize(100);
		pageableResolver.setOneIndexedParameters(false);
		pageableResolver.setPageParameterName("page");
		pageableResolver.setSizeParameterName("size");
		pageableResolver.setFallbackPageable(defaultPage);

		argumentResolvers.add(sortHandlerMethodArgumentResolver);
		argumentResolvers.add(pageableResolver);
	}

}
