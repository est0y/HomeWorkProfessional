package ru.petrelevich.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import ru.petrelevich.repository.MessageRepository;
import ru.petrelevich.services.MessageService;
import ru.petrelevich.services.MessageServiceImpl;
import ru.petrelevich.services.SecretRoomDecorator;

@Configuration
public class MessageServiceConfig {
    @Autowired
    private MessageRepository repository;
    @Autowired
    private SimpMessagingTemplate template;


    @Bean
    public MessageService messageServiceImpl() {
        return new MessageServiceImpl(repository, template);
    }

    @Primary
    @Bean
    public MessageService secretRoomDecorator(@Value("${secretRoom.id}") String secretRoomId,
                                              MessageService messageServiceImpl) {
        return new SecretRoomDecorator(secretRoomId, messageServiceImpl, repository, template);
    }
}
