package ru.petrelevich.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.util.HtmlUtils;
import ru.petrelevich.domain.Message;
import ru.petrelevich.repository.MessageRepository;

public class MessageServiceImpl implements MessageService {
    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
    private final MessageRepository repository;
    private final SimpMessagingTemplate template;

    public MessageServiceImpl(MessageRepository repository, SimpMessagingTemplate template) {
        this.repository = repository;
        this.template = template;
    }

    @Override
    public void handleMessage(String roomId, Message message, String topicTemplate) {
        repository.save(roomId, message)
                .subscribe(msgId -> logger.info("message send id:{}", msgId));
        template.convertAndSend(String.format("%s%s", topicTemplate, roomId),
                new Message(HtmlUtils.htmlEscape(message.messageStr())));
    }

    @Override
    public void loadRoomMessages(String roomId, String simpDestination) {
        repository.findByRoomId(roomId)
                .doOnError(ex -> logger.error("getting messages for roomId:{} failed", roomId, ex))
                .subscribe(message -> template.convertAndSend(simpDestination, message));
    }
}
