package ru.otus.config;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.util.List;
import java.util.TreeSet;

@AppComponentsContainerConfig(order = 2)
public class AppConfig1 {
    @AppComponent(order = 0, name = "treeSetFromList")
    public TreeSet<Integer> treeSetFromList(List<Integer> list) {
        return new TreeSet<>(list);
    }
}
