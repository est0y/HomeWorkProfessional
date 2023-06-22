package ru.petrelevich.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.petrelevich.domain.Message;

public interface MessageRepository {
    Flux<Message> findByRoomId(String roomId);

    Flux<Message> findAll();

    Mono<Long> save(String roomId, Message message);

}
