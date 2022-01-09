package com.lomalan.main.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
@Data
@PropertySource("classpath:application.properties")
public class BotConfig {
  @Value("${bot.token}")
  private String token;
  @Value("${bot.name}")
  private String name;
  @Value("${bot.webhookUrl}")
  private String webHookUrl;
  @Value("${mongo.host}")
  private String mongoHost;

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }

  @Bean
  public MongoClient mongo() {
    ConnectionString connectionString = new ConnectionString(mongoHost);
    MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
        .applyConnectionString(connectionString)
        .build();

    return MongoClients.create(mongoClientSettings);
  }

  @Bean
  public MongoTemplate mongoTemplate() {
    return new MongoTemplate(mongo(), "test");
  }
}
