package org.harisux.fullstackplay.exception;

import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class ResponseExceptionHandler {

    private static final Logger LOG = Logger.getLogger(ResponseExceptionHandler.class);
    
    public Response handle(ThrowingSupplier<Response> callback) {
        try {
            return callback.get();
        } catch (FilmNotFoundException exp) {
            return Response.status(404)
                    .entity(exp.getMessage()).build();   
        } catch (Exception exp) {
            LOG.error(exp.getMessage());
            return Response.status(500)
                    .entity("Something went wrong...").build();   
        }
    }

}
