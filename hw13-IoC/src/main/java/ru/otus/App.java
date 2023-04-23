package ru.otus;

import ru.otus.appcontainer.AppComponentsContainerImpl;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.config.AppConfig;
import ru.otus.config.AppConfig1;
import ru.otus.config.AppConfig2;
import ru.otus.services.GameProcessor;
import ru.otus.services.GameProcessorImpl;
import ru.otus.services.PlayerService;

import java.util.Set;

/*
В классе AppComponentsContainerImpl реализовать обработку, полученной в конструкторе конфигурации,
основываясь на разметке аннотациями из пакета appcontainer. Так же необходимо реализовать методы getAppComponent.
В итоге должно получиться работающее приложение. Менять можно только класс AppComponentsContainerImpl.
Можно добавлять свои исключения.

Раскоментируйте тест:
@Disabled //надо удалить
Тест и демо должны проходить для всех реализованных вариантов
Не называйте свой проект ДЗ "homework-template", это имя заготовки)

PS Приложение представляет собой тренажер таблицы умножения
*/

public class App {

    public static void main(String[] args) throws Exception {
        //homework();
        fullHomework();
    }

    private static void homework() {
        // Обязательный вариант
        AppComponentsContainer container = new AppComponentsContainerImpl(AppConfig.class);

        // Приложение должно работать в каждом из указанных ниже вариантов
        //GameProcessor gameProcessor = container.getAppComponent(GameProcessor.class);
        //GameProcessor gameProcessor = container.getAppComponent(GameProcessorImpl.class);
        GameProcessor gameProcessor = container.getAppComponent("gameProcessor");
        gameProcessor.startGame();
    }

    private static void fullHomework() {
        // Тут можно использовать библиотеку Reflections (см. зависимости)
        AppComponentsContainer container = new AppComponentsContainerImpl("ru.otus.config");
        // Приложение должно работать в каждом из указанных ниже вариантов
        //GameProcessor gameProcessor = container.getAppComponent(GameProcessor.class);
        //GameProcessor gameProcessor = container.getAppComponent(GameProcessorImpl.class);
        GameProcessor gameProcessor = container.getAppComponent("gameProcessor");
        Set<Integer> setFromList = container.getAppComponent("treeSetFromList");
        System.out.println("Отсортированная коллекция: " + setFromList);
        gameProcessor.startGame();
    }
}
