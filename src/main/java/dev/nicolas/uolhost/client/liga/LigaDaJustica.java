package dev.nicolas.uolhost.client.liga;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("liga_da_justica")
public record LigaDaJustica(Codinomes codinomes) {
    public record Codinomes(List<String> codinome) {
    }
}
