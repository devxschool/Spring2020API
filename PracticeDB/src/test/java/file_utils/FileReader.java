package file_utils;

import gherkin.lexer.Fi;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class FileReader {
    private String path;
    private File file;

    public FileReader(String path){
        this.path = path;
        this.file = new File(path);
    }

    public List<String> readLine() throws IOException {
        return FileUtils.readLines(file, Charset.defaultCharset());
    }
}
