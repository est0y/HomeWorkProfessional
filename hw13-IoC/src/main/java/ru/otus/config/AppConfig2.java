package ru.otus.config;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.util.List;

@AppComponentsContainerConfig(order = 1)
public class AppConfig2 {
    @AppComponent(order = 0, name = "integersList")
    public List<Integer> integersList() {
        return List.of(5, 3, 1, 4, 2);
    }

}
