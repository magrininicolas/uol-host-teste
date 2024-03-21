package dev.nicolas.uolhost.client.liga;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "LigaClient", url = "https://raw.githubusercontent.com/uolhost/test-backEnd-Java/master/referencias/liga_da_justica.xml")
public interface LigaClient {

    @GetMapping
    LigaDaJustica getLigaDaJustica();
}
