package com.koc.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}


//	@Bean
//	public RouteLocator customRouteLocator(RouteLocatorBuilder builder, ThrottleGatewayFilterFactory throttle) {
//		return builder.routes()
//				.route(r -> r.host("**.abc.org").and().path("/image/png")
//						.filters(f ->
//								f.addResponseHeader("X-TestHeader", "foobar"))
//						.uri("http://httpbin.org:80")
//				)
//				.route(r -> r.path("/image/webp")
//						.filters(f ->
//								f.addResponseHeader("X-AnotherHeader", "baz"))
//						.uri("http://httpbin.org:80")
//						.metadata("key", "value")
//				)
//				.route(r -> r.order(-1)
//						.host("**.throttle.org").and().path("/get")
//						.filters(f -> f.filter(throttle.apply(1,
//								1,
//								10,
//								TimeUnit.SECONDS)))
//						.uri("http://httpbin.org:80")
//						.metadata("key", "value")
//				)
//				.build();
//	}
}
