import static spark.Spark.get;

/**
 * Created by moshiour.rahman on 8/10/2016.
 */
public class HelloWorld {
    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello World");
    }
}
