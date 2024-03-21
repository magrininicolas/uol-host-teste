package dev.nicolas.uolhost.client.vingadores;

import java.util.List;

public record Vingadores(List<Heroi> vingadores) {
    public record Heroi(String codinome) {
    }
}
