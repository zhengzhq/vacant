package com.vacant;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

import com.vacant.lookup.LookupService;

@Component
public class VacantDialect extends AbstractDialect implements IExpressionObjectDialect {

	@Autowired
	private LookupService lookupService;
	
	private IExpressionObjectFactory expressionObjectFactory = null;

	public static final String EXPRESSION_OBJECT_NAME_LOOKUP = "lookups";

	public VacantDialect() {
		super("vacant");
	}

	@Override
	public IExpressionObjectFactory getExpressionObjectFactory() {
		if (expressionObjectFactory != null) {
			return expressionObjectFactory;
		}
		return new IExpressionObjectFactory() {

			@Override
			public boolean isCacheable(String expressionObjectName) {
				return expressionObjectName != null;
			}

			@Override
			public Set<String> getAllExpressionObjectNames() {
				return Collections.unmodifiableSet(new LinkedHashSet<String>(
						java.util.Arrays.asList(new String[] { EXPRESSION_OBJECT_NAME_LOOKUP })));
			}

			@Override
			public Object buildObject(IExpressionContext context, String expressionObjectName) {
				if (EXPRESSION_OBJECT_NAME_LOOKUP.equals(expressionObjectName)) {
					return lookupService;
				}
				return null;
			}
		};
	}

}
