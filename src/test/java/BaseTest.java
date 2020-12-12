import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BaseTest {
    protected String resource = "src/main/resources/blog-mapping.xml";

    protected String getXmlString(String resource) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(resource))) {
            StringBuilder sb = new StringBuilder();
            String temp;
            while ((temp = bufferedReader.readLine()) != null) {
                sb.append(temp);
            }
            return sb.toString();
        }
    }
}
