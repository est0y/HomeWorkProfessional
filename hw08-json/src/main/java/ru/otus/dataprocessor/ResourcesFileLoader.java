package ru.otus.dataprocessor;

import com.google.gson.Gson;
import ru.otus.model.Measurement;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ResourcesFileLoader implements Loader {
    private final String filename;

    public ResourcesFileLoader(String fileName) {
        this.filename = fileName;
    }

    @Override
    public List<Measurement> load() {
        //читает файл, парсит и возвращает результат
        try (Reader reader = new InputStreamReader(Objects.requireNonNull(ResourcesFileLoader.class.getClassLoader()
                .getResourceAsStream(filename)))) {
            return List.of(new Gson().fromJson(reader, Measurement[].class));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
