package dev.nicolas.uolhost.client.vingadores;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "VingadoresClient", url = "https://www.jsonkeeper.com/b/VAC3")
public interface VingadoresClient {

    @GetMapping
    Vingadores getCodinomesVingadores();
}
