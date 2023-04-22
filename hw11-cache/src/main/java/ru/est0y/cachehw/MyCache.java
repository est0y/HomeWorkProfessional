package ru.est0y.cachehw;


import java.util.*;


public class MyCache<V> implements HwCache<String, V> {
    //Надо реализовать эти методы
    private final WeakHashMap<String, V> map = new WeakHashMap<>();
    private final Set<HwListener<String, V>> listeners = new HashSet<>();

    @Override
    public void put(String key, V value) {
        notifyListeners(key, value, "put");

        map.put(key, value);
    }

    @Override
    public void remove(String key) {
        notifyListeners(key, map.get(key), "remove");
        map.remove(key);
    }

    @Override
    public V get(String key) {
        V value = map.get(key);
        notifyListeners(key, value, "get");
        return value;
    }

    @Override
    public void addListener(HwListener<String, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<String, V> listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(String key, V value, String action) {
        listeners.forEach(listener -> listener.notify(key, value, action));
    }
}
