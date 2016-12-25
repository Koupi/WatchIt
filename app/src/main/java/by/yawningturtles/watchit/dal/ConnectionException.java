package by.yawningturtles.watchit.dal;

import java.sql.Connection;

/**
 * Created by marija.savtchouk on 25.12.2016.
 */

public class ConnectionException extends Exception {
    private static final long serialVersionUID = 1L;

    public 	ConnectionException(){
        super("Check your internet connection");
    }

    public ConnectionException(String message){
        super(message);
    }

    public ConnectionException(String message, Throwable cause){
        super(message , cause);
    }

    public ConnectionException(Throwable cause){
        super(cause);
    }
}
