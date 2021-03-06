package com.github.erodriguezg.springbootswf.config;

import com.github.erodriguezg.springbootswf.scopes.ViewScope;
import com.github.erodriguezg.springbootswf.utils.ConstantesUtil;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.faces.config.AbstractFacesFlowConfiguration;
import org.springframework.faces.webflow.FlowFacesContextLifecycleListener;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.security.SecurityFlowExecutionListener;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class WebFlowConfig extends AbstractFacesFlowConfiguration {

    @Bean
    public FlowExecutor flowExecutor(FlowDefinitionRegistry flowDefinitionRegistry) {
        return getFlowExecutorBuilder(flowDefinitionRegistry)
                .setMaxFlowExecutions(5)
                .setMaxFlowExecutionSnapshots(-1)
                .addFlowExecutionListener(new FlowFacesContextLifecycleListener())
                .addFlowExecutionListener(new SecurityFlowExecutionListener())
                .build();
    }

    @Bean
    public FlowDefinitionRegistry flowRegistry(FlowBuilderServices flowBuilderServices) {
        return getFlowDefinitionRegistryBuilder(flowBuilderServices)
                .setBasePath("/WEB-INF/flows")
                .addFlowLocationPattern("/**/*-flow.xml")
                .build();
    }

    @Bean
    @Profile(ConstantesUtil.SPRING_BOOT_PROFILE_NAME_DEVELOPMENT)
    public FlowBuilderServices flowBuilderServicesDevelopment() {
        return getFlowBuilderServicesBuilder()
                .setDevelopmentMode(true)
                .build();
    }

    @Bean
    @Profile(ConstantesUtil.SPRING_BOOT_PROFILE_NAME_PRODUCTION)
    public FlowBuilderServices flowBuilderServicesProduction() {
        return getFlowBuilderServicesBuilder()
                .setDevelopmentMode(false)
                .build();
    }

    @Bean
    public CustomScopeConfigurer customScopeConfigurer() {
        CustomScopeConfigurer customScopeConfigurer = new CustomScopeConfigurer();
        Map<String, Object> scopeMaps = new HashMap<>();
        scopeMaps.put("flow", new org.springframework.webflow.scope.FlowScope());
        scopeMaps.put("conversation", new org.springframework.webflow.scope.ConversationScope());
        scopeMaps.put("viewflow", new org.springframework.webflow.scope.ViewScope());
        scopeMaps.put("view", new ViewScope());
        customScopeConfigurer.setScopes(scopeMaps);
        return customScopeConfigurer;
    }

}
