package com.adidas.solrchallenge.config;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;


@Configuration
@EnableSolrRepositories(basePackages = {"com.adidas.solrchallenge.repository"}, multicoreSupport = true)
@EnableConfigurationProperties(SolrProperties.class)
public class SolrConfig {

    private final SolrProperties solrProperties;

    @Autowired
    public SolrConfig(SolrProperties solrProperties) {
        this.solrProperties = solrProperties;
    }

    @Bean
    public SolrClient solrClient() {
        return new HttpSolrClient(solrProperties.getProtocol()
                + "://" + solrProperties.getHost()
                + ":" + solrProperties.getPort()
                + "/solr");
    }

    @Bean
    public SolrTemplate solrTemplate(SolrClient solrClient) {
        return new SolrTemplate(solrClient);
    }

    @Bean
    public Jackson2RepositoryPopulatorFactoryBean repositoryPopulator() {
        Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
        factory.setResources(new Resource[]{new ClassPathResource("data.json")});
        return factory;
    }

}
