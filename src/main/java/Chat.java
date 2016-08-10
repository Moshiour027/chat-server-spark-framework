
import org.eclipse.jetty.websocket.api.*;
import org.json.*;
import java.text.*;
import java.util.*;
import static j2html.TagCreator.*;
import static spark.Spark.*;

/**
 * Created by moshiour.rahman on 8/10/2016.
 */
public class Chat {

    static Map<Session, String> userUsernameMap = new HashMap<>();
    static int nextUserNumber = 1; //Used for creating the next username

    public static void main(String[] args) {
        staticFileLocation("/public"); //index.html is served at localhost:4567 (default port)
        webSocket("/chat", ChatWebSocketHandler.class);
        init();
    }
    public static void broadcastMessage(String sender, String message) {
        userUsernameMap.keySet().stream().filter(Session::isOpen).forEach(session -> {
            try {
                session.getRemote().sendString(String.valueOf(new JSONObject()
                    .put("userMessage", createHtmlMessageFromSender(sender, message))
                    .put("userlist", userUsernameMap.values())
                ));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static String createHtmlMessageFromSender(String sender, String message) {
        return article().with(
            b(sender + " says:"),
            p(message),
            span().withClass("timestamp").withText(new SimpleDateFormat("HH:mm:ss").format(new Date()))
        ).render();
    }
}
