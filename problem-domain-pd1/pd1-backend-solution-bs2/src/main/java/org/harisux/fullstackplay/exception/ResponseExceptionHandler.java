package org.harisux.fullstackplay.exception;

import org.apache.http.HttpStatus;
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
            return Response.status(HttpStatus.SC_NOT_FOUND)
                    .entity(exp.getMessage()).build();   
        } catch (MissingParameterException exp) {
            return Response.status(HttpStatus.SC_BAD_REQUEST)
                    .entity(exp.getMessage()).build();   
        } catch (Exception exp) {
            LOG.error(exp.getMessage());
            return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                    .entity("Something went wrong...").build();   
        }
    }

}
