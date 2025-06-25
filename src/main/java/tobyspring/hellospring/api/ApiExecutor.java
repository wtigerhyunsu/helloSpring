package tobyspring.hellospring.api;

import java.io.IOException;
import java.net.URL;

public interface ApiExecutor {
    String execute(URL uri) throws IOException;
}
