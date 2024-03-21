package dev.nicolas.uolhost.services;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import dev.nicolas.uolhost.client.vingadores.Vingadores.Heroi;
import dev.nicolas.uolhost.client.vingadores.VingadoresClient;
import dev.nicolas.uolhost.models.Jogador;
import dev.nicolas.uolhost.repository.JogadorRepository;

@Service
public class JogadorService {

    private JogadorRepository jogadorRepository;
    private VingadoresClient vingadoresClient;

    public JogadorService(JogadorRepository jogadorRepository, VingadoresClient vingadoresClient) {
        this.jogadorRepository = jogadorRepository;
        this.vingadoresClient = vingadoresClient;
    }

    public String getJogadorCodinome(Jogador jogador) {
        List<String> codinomesBanco = jogadorRepository
                .findAllCodinomeByGrupo(jogador.getGrupo())
                .stream()
                .map(Jogador::getCodinome)
                .collect(Collectors.toList());

        if (jogador.getGrupo().equals("Vingadores")) {
            String vingador = getVingador(codinomesBanco);
            jogador.setCodinome(vingador);
            return vingador;
        } else if (jogador.getGrupo().equals("Liga da Justiça")) {
            String liga = getLiga(codinomesBanco);
            jogador.setCodinome(liga);
            return liga;
        }
        return null;
    }

    private String getVingador(List<String> codinomes) {
        List<Heroi> herois = vingadoresClient.getCodinomesVingadores().vingadores();

        for (Heroi heroi : herois) {
            if (!codinomes.contains(heroi.codinome())) {
                return heroi.codinome();
            }
        }

        return null;
    }

    private String getLiga(List<String> codinomes) {
        try {
            File file = new File("src/main/resources/liga/liga.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);
            NodeList nl = doc.getElementsByTagName("codinome");

            for (int i = 0; i < nl.getLength(); i++) {
                Element eElement = (Element) nl.item(i);
                String codinome = eElement.getTextContent();

                if (!codinomes.contains(codinome)) {
                    return codinome;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
