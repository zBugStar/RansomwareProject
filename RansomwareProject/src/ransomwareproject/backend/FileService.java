package aes.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileService {

    public String readFile(String filePath) throws IOException {
        byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
        return new String(fileBytes, StandardCharsets.UTF_8);
    }

    public void writeFile(String filePath, String content) throws IOException {
        Files.write(Paths.get(filePath), content.getBytes(StandardCharsets.UTF_8));
    }

    public void checkFileAccess(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            throw new IOException("El archivo no existe: " + filePath);
        }
        if (!Files.isReadable(path)) {
            throw new IOException("El archivo no es legible: " + filePath);
        }
        if (!Files.isWritable(path)) {
            throw new IOException("El archivo no es escribible: " + filePath);
        }
    }
}

