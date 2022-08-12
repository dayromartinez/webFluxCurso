package co.com.sofka.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class Handler {

    private Flux<String> tiposGalletas = Flux.fromIterable(Arrays.asList("tostadas", "Marías", "troqueladas", "cracker", "barquillos",
            "bizcochos", "sandwiches", "pastas blandas", "pastas duras", "pastas recubiertas de chocolate"));

    public Mono<ServerResponse> listenGETUseCaseSearchTypeCookie(ServerRequest serverRequest) {
        String tipoGalleta = serverRequest.queryParam("tipoGalleta").orElse("nojoda");
        return ServerResponse.ok().body(searchTypeOfCookie(tipoGalleta), String.class);
    }


    private Flux<String> searchTypeOfCookie(String tipoGalleta) {
        return tiposGalletas
                .filter(galleta -> galleta.equals(tipoGalleta.toLowerCase(Locale.ROOT).trim()))
                .switchIfEmpty(Flux.error(new IOException("El tipo de galleta buscado no se encuentra registrado")))
                .map(galletaFiltrada -> "El tipo de galleta "+ galletaFiltrada + " se encuentra entre los tipos de galletas disponibles");
    }

}
