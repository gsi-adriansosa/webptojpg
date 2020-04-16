import io.github.biezhi.webp.WebpIO;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class main {

    public static void main(String[] args) {
        try {
            FileReader reader = new FileReader("path.properties");
            Properties p = new Properties();
            p.load(reader);
            String dirPathIn = p.getProperty("dirPath");
            Stream<Path> files = Files.walk(Paths.get(dirPathIn));
            List<String> allFiles = files.filter(f -> f.getFileName().toString().endsWith(".jpg")).map(x -> x.getFileName().toString()).collect(Collectors.toList());
            for (String fileName : allFiles) {
                WebpIO.create().toNormalImage(dirPathIn + "\\" + fileName, dirPathIn + "\\" + fileName);
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
            System.out.println(e.getMessage());
        }
    }


}
