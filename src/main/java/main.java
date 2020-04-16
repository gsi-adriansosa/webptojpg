import io.github.biezhi.webp.WebpIO;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
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
            String dirPath = p.getProperty("dirPath");
            Date startDate = new SimpleDateFormat("MM/dd/yyyy").parse(p.getProperty("startDate"));
            Date endDate = new SimpleDateFormat("MM/dd/yyyy").parse(p.getProperty("endDate"));
            Stream<Path> files = Files.walk(Paths.get(dirPath));
            List<String> allFiles = files.filter(f -> f.getFileName().toString().endsWith(".jpg")).map(x -> x.getFileName().toString()).collect(Collectors.toList());
            for (String fileName : allFiles) {
                File sourceImage = new File(dirPath + "\\" + fileName);
                BasicFileAttributes attr = Files.readAttributes(sourceImage.toPath(), BasicFileAttributes.class);
                if (new Date(attr.lastModifiedTime().toMillis()).compareTo(startDate) >= 0 && new Date(attr.lastModifiedTime().toMillis()).compareTo(endDate) <= 0) {
                    WebpIO.create().toNormalImage(dirPath + "\\" + fileName, dirPath + "\\" + fileName);
                }
            }
        } catch (Exception e) {
            System.out.println("Something went wrong");
            System.out.println(e.getMessage());
        }
    }


}
