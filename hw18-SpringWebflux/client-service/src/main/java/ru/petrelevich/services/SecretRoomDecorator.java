package ru.petrelevich.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.util.HtmlUtils;
import ru.petrelevich.domain.Message;
import ru.petrelevich.repository.MessageRepository;


public class SecretRoomDecorator implements MessageService {
    private static final Logger logger = LoggerFactory.getLogger(SecretRoomDecorator.class);
    private final String secretRoomId;
    private final MessageService service;
    private final MessageRepository repository;
    private final SimpMessagingTemplate template;

    public SecretRoomDecorator(String secretRoomId,
                               MessageService service,
                               MessageRepository repository,
                               SimpMessagingTemplate template) {
        this.secretRoomId = secretRoomId;
        this.service = service;
        this.repository = repository;
        this.template = template;
    }

    @Override
    public void handleMessage(String roomId, Message message, String topicTemplate) {
        if (secretRoomId.equals(roomId)) {
            throw new SecretRoomException("Unexpected message sent in secret room");
        }
        template.convertAndSend(String.format("%s%s", topicTemplate, "1408"),
                new Message(HtmlUtils.htmlEscape(message.messageStr())));
        service.handleMessage(roomId, message, topicTemplate);
    }

    @Override
    public void loadRoomMessages(String roomId, String simpDestination) {
        if (secretRoomId.equals(roomId)) {
            repository.findAll()
                    .doOnError(ex -> logger.error("getting all messages for roomId:{} failed", roomId, ex))
                    .subscribe(message -> template.convertAndSend(simpDestination, message));
        } else {
            service.loadRoomMessages(roomId, simpDestination);
        }
    }

}
