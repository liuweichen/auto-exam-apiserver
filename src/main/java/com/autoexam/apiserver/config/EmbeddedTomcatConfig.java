package com.autoexam.apiserver.config;


import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class EmbeddedTomcatConfig implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
  @Override
  public void customize(ConfigurableServletWebServerFactory factory) {
    ((TomcatServletWebServerFactory) factory).addConnectorCustomizers(connector -> {
      Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
      try {
        protocol.setAddress(InetAddress.getLocalHost());
      } catch (UnknownHostException e) {
        e.printStackTrace();
      }
    });
  }
}
