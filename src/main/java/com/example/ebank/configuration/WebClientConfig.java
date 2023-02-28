package com.example.ebank.configuration;

import java.io.IOException;

import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import io.vavr.control.Option;
import reactor.netty.http.client.HttpClient;
// import reactor.netty.transport.ProxyProvider;

@Configuration
public class WebClientConfig {
    
    // @Value("${web.client.config.connectTimeout}")
    // private int connectTimeout;
    
    // @Value("${web.client.config.readTimeout}")
    // private int readTimeout;
    
    // @Value("${web.client.config.writeTimeout}")
    // private int writeTimeout;
    
    // // String value (default is empty). Specifies the hostname of the authenticated proxy server. If the value is empty,
    // // no proxy is used.
    // @Value("${example.proxy.host}")
    // private String proxyHost;
    
    // // Number that specifies the port of the authenticated proxy server (default is empty).
    // @Value("${example.proxy.port}")
    // private Integer proxyPort;
    
    // @Value("${spring.profiles.active}")
    // private String activeProfile;
    
    // @Value("${spring.profiles.release.name}")
    // private String profileRelaseName;
    
    // @Bean
    // public WebClient webClientWithTimeout() {
        
    //     var httpClient = HttpClient.create()
    //             .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectTimeout)
    //             .doOnConnected(connection -> {
    //                 connection.addHandlerLast(new ReadTimeoutHandler(readTimeout, TimeUnit.MILLISECONDS));
    //                 connection.addHandlerLast(new WriteTimeoutHandler(writeTimeout, TimeUnit.MILLISECONDS));
    //             });
        
    //     // Set proxy if needed based on application.properties file
    //     httpClient = Option.of(proxyPort)
    //             .isEmpty()
    //             || Option.of(proxyHost.trim())
    //                     .isEmpty() ? httpClient
    //                             : httpClient
    //                                     .proxy(proxy -> proxy.type(ProxyProvider.Proxy.HTTP)
    //                                             .host(proxyHost)
    //                                             .port(proxyPort));
        
    //     return WebClient.builder()
    //             .clientConnector(new ReactorClientHttpConnector(httpClient))
    //             .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
    //             .build();
    // }
    
}