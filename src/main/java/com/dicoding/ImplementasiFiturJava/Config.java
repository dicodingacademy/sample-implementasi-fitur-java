package com.dicoding.ImplementasiFiturJava;

import com.linecorp.bot.client.LineBlobClient;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.parser.LineSignatureValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.net.URI;

@Configuration
@PropertySource("classpath:application.properties")
public class Config {
    @Autowired
    private Environment mEnv;

    @Bean(name = "line.bot.channelSecret")
    public String getChannelSecret() {
        return mEnv.getProperty("line.bot.channelSecret");
    }

    @Bean(name = "line.bot.channelToken")
    public String getChannelAccessToken() {
        return mEnv.getProperty("line.bot.channelToken");
    }

    @Bean(name = "lineMessagingClient")
    public LineMessagingClient getMessagingClient() {
        return LineMessagingClient
                .builder(getChannelAccessToken())
                .apiEndPoint(URI.create("https://api.line.me/"))
                .connectTimeout(10_000)
                .readTimeout(10_000)
                .writeTimeout(10_000)
                .build();
    }

    @Bean(name = "lineBlobClient")
    public LineBlobClient getContentClient() {
        return LineBlobClient
                .builder(getChannelAccessToken())
                .apiEndPoint(URI.create("https://api-data.line.me/"))
                .connectTimeout(10_000)
                .readTimeout(10_000)
                .writeTimeout(10_000)
                .build();
    }

    @Bean(name = "lineSignatureValidator")
    public LineSignatureValidator getSignatureValidator() {
        return new LineSignatureValidator(getChannelSecret().getBytes());
    }
}