package br.uespi.phb;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class CorsFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        
        // Autoriza a origem do seu front-end (Live Server)
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
        
        // Autoriza os cabeçalhos que o navegador pede no preflight
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        
        // Autoriza os métodos que a sua API usa
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        
        // Permite envio de credenciais (opcional, mas recomendado)
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
    }
}