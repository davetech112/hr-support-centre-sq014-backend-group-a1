package com.hrsupportcentresq014.hrsupportcentresq014.services.serviceImpl;

import com.hrsupportcentresq014.hrsupportcentresq014.services.ThymeleafService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.Map;

@Service
public class ThymeleafServiceImpl implements ThymeleafService {
    @Value("${mail.templateBaseName}")
    private static String MAIL_TEMPLATE_BASE_NAME;

    @Value("${mail.templatePrefix}")
    private static String MAIL_TEMPLATE_PREFIX;

    @Value("${mail.templateSuffix}")
    private static String MAIL_TEMPLATE_SUFFIX;

    @Value("${mail.charset}")
    private static String UTF_8;
    private static TemplateEngine templateEngine;
    static {
        templateEngine = emailTemplateEngine();
    }
    private static TemplateEngine emailTemplateEngine() {
        final SpringTemplateEngine templateEngine1 = new SpringTemplateEngine();

        templateEngine1.setTemplateResolver(htmlTemplateResolver());
        templateEngine1.setTemplateEngineMessageSource(emailMessageSource());

        return templateEngine1;
    }
    private static ITemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix(MAIL_TEMPLATE_PREFIX);
        templateResolver.setSuffix(MAIL_TEMPLATE_SUFFIX);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(UTF_8);
        templateResolver.setCacheable(false);

        return templateResolver;
    }
    private static ResourceBundleMessageSource emailMessageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(MAIL_TEMPLATE_BASE_NAME);

        return messageSource;
    }
    @Override
    public String createContent(String template, Map<String, Object> variables) {
        final Context context = new Context();
        context.setVariables(variables);
        return templateEngine.process(template, context);
    }
}
