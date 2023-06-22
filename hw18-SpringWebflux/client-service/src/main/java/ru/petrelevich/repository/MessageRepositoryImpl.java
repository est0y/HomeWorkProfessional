package ru.petrelevich.repository;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.petrelevich.domain.Message;

@Component
public class MessageRepositoryImpl implements MessageRepository {
    private final WebClient datastoreClient;

    public MessageRepositoryImpl(WebClient datastoreClient) {
        this.datastoreClient = datastoreClient;
    }

    @Override
    public Flux<Message> findByRoomId(String roomId) {
        return datastoreClient.get().uri(String.format("/msg/%s", roomId))
                .accept(MediaType.APPLICATION_NDJSON)
                .exchangeToFlux(response -> {
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return response.bodyToFlux(Message.class);
                    } else {
                        return response.createException().flatMapMany(Mono::error);
                    }
                });
    }

    @Override
    public Flux<Message> findAll() {
        return datastoreClient.get().uri("/allMsg")
                .accept(MediaType.APPLICATION_NDJSON)
                .exchangeToFlux(response -> {
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return response.bodyToFlux(Message.class);
                    } else {
                        return response.createException().flatMapMany(Mono::error);
                    }
                });
    }

    @Override
    public Mono<Long> save(String roomId, Message message) {
        return datastoreClient.post().uri(String.format("/msg/%s", roomId))
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(message)
                .exchangeToMono(response -> response.bodyToMono(Long.class));
    }
}
