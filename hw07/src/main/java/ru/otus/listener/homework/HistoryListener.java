package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.HashMap;
import java.util.Optional;
import java.util.TreeMap;

public class HistoryListener implements Listener, HistoryReader {
    private final HashMap<Long, Message> map = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        var clone = msg.clone();
        map.put(clone.getId(), clone);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(map.get(id).clone());
    }
}
