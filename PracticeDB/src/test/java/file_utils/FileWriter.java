package file_utils;

import gherkin.lexer.Fi;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileWriter {
    private String path;
    private File file;

    public FileWriter(String path){
        this.path = path;
        this.file = new File(path);
    }

    public void writeLine(String line) throws IOException {
        FileUtils.writeStringToFile(file, line, true);
    }

    public <T> void writeLines(List<T> lines) throws IOException {
        FileUtils.writeLines(file, lines);
    }
}
