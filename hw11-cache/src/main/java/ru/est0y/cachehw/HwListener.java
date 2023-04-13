package ru.est0y.cachehw;


public interface HwListener<K, V> {
    void notify(K key, V value, String action);
}
