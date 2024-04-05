package top.griseo.telegram.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Manager {
    private final File location;
    @Getter
    private final Instance config;

    public Manager(File location) throws IOException {
        this.location = location;
        if (location.exists()) {
            config = new Gson().fromJson(Files.readString(location.toPath()), Instance.class);
        } else {
            config = new Instance();
            save();
        }
    }

    public void save() throws IOException {
        String format = new GsonBuilder().serializeNulls().setPrettyPrinting().create().toJson(config);
        if (location.exists() && !location.delete())
            throw new IOException(String.format("Failed delete file: %s", location.getAbsolutePath()));
        Files.copy(new ByteArrayInputStream(format.getBytes(StandardCharsets.UTF_8)), location.toPath());
    }
}
