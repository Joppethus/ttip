package com.searcheveryaspect.backend.webserver;

import com.beust.jcommander.JCommander;
import com.searcheveryaspect.backend.ESQuerier;
import com.searcheveryaspect.backend.TrendingQuerier;
import com.searcheveryaspect.backend.webserver.controller.CategoryController;
import com.searcheveryaspect.backend.webserver.controller.MotionsController;
import com.searcheveryaspect.backend.webserver.controller.TrendingController;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.restexpress.RestExpress;
import org.restexpress.exception.ServiceException;
import org.restexpress.response.ErrorResponseWrapper;
import org.restexpress.serialization.AbstractSerializationProvider;
import org.restexpress.serialization.json.JacksonJsonProcessor;

/**
 * Start a ttip webserver with a elasticSearch node. Flag -port sets port for traffic, default is 8080.
 */
public class Main {

  public static void main(String[] args) {
    CommandLineArgs cla = new CommandLineArgs();
    new JCommander(cla, args);

    RestExpress.setSerializationProvider(new AbstractSerializationProvider() {
      {
        add(new JacksonJsonProcessor(), new ErrorResponseWrapper(), true);
      }
    });
    RestExpress server = new RestExpress().setName("SEA").setPort(cla.port.intValue());

    final Node node = NodeBuilder.nodeBuilder().client(true).node().start();
    final Client client = node.client();

    defineRoutes(server, client);

    server.setIoThreadCount(4);
    server.setExecutorThreadCount(4);

    mapExceptions(server);
    server.bind();
    server.awaitShutdown();
  }

  // The allowed routes the server responds to.
  private static void defineRoutes(RestExpress server, Client client) {
    server.uri("/mot/{category}/{interval}/from/{from_date}/to/{to_date}", new MotionsController(
        ESQuerier.newReader(client)));
    server.uri("/mot/top/{quatity}", new TrendingController(TrendingQuerier.newReader(client)));
    server.uri("/categories", new CategoryController());
  }

  private static void mapExceptions(RestExpress server) {
    server.mapException(RuntimeException.class, ServiceException.class);
  }
}
