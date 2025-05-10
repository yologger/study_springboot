import reactor.core.publisher.Mono
import reactor.netty.DisposableServer
import reactor.netty.http.server.HttpServer
import reactor.netty.http.server.HttpServerRequest
import reactor.netty.http.server.HttpServerResponse
import reactor.netty.http.server.HttpServerRoutes

fun main() {
    val server: DisposableServer = HttpServer.create()
        .port(8080)
        .route { routes: HttpServerRoutes ->
            routes.get("/user") { request: HttpServerRequest, response: HttpServerResponse ->
                response.sendString(Mono.just("GET request to /user"))
            }
            routes.post("/user") { request: HttpServerRequest, response: HttpServerResponse ->
                response.sendString(Mono.just("POST request to /user"))
            }
            routes.put("/user") { request: HttpServerRequest, response: HttpServerResponse ->
                response.sendString(Mono.just("PUT request to /user"))
            }
            routes.delete("/user") { request: HttpServerRequest, response: HttpServerResponse ->
                response.sendString(Mono.just("DELETE request to /user"))
            }
        }
        .bindNow()

    println("Server started on port ${server.port()}")
    server.onDispose().block()
}