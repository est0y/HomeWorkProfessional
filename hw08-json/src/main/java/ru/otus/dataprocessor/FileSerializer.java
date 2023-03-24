package ru.otus.dataprocessor;

import com.google.gson.Gson;
import ru.otus.model.Measurement;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipOutputStream;

public class FileSerializer implements Serializer {

    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        //формирует результирующий json и сохраняет его в файл
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            new Gson().toJson(data, fileWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
