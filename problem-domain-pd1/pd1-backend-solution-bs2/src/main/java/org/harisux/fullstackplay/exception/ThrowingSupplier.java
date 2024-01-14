package org.harisux.fullstackplay.exception;

@FunctionalInterface
public interface ThrowingSupplier<T> {

    default T get() throws Exception {
        T result = null;
        try {
            result = acceptThrows();
        } catch(Exception exp) {
            //No need to handle here
            throw exp;
        }
        return result;
    }

    T acceptThrows() throws Exception;
    
}
