import java.io.IOException;

public interface BookCommands {
    void proceedOrder(String s);
    void proceedUpdate(String s);
    void proceedQuery(String s) throws IOException;
}
