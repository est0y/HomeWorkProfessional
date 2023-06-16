package ru.petrelevich.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import ru.petrelevich.domain.Message;
import ru.petrelevich.services.MessageService;

@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    private static final String TOPIC_TEMPLATE = "/topic/response.";
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/message.{roomId}")
    public void getMessage(@DestinationVariable String roomId, Message message) {
        logger.info("get message:{}, roomId:{}", message, roomId);
        messageService.handleMessage(roomId, message, TOPIC_TEMPLATE);
    }


    @EventListener
    public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
        var genericMessage = (GenericMessage<byte[]>) event.getMessage();
        var simpDestination = (String) genericMessage.getHeaders().get("simpDestination");
        if (simpDestination == null) {
            logger.error("Can not get simpDestination header, headers:{}", genericMessage.getHeaders());
            throw new ChatException("Can not get simpDestination header");
        }
        var roomId = parseRoomId(simpDestination);
        messageService.loadRoomMessages(String.valueOf(roomId), simpDestination);
    }

    private long parseRoomId(String simpDestination) {
        try {
            return Long.parseLong(simpDestination.replace(TOPIC_TEMPLATE, ""));
        } catch (Exception ex) {
            logger.error("Can not get roomId", ex);
            throw new ChatException("Can not get roomId");
        }
    }
}
