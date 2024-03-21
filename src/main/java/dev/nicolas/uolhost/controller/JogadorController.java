package dev.nicolas.uolhost.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.nicolas.uolhost.models.Jogador;
import dev.nicolas.uolhost.repository.JogadorRepository;
import dev.nicolas.uolhost.services.JogadorService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/jogadores")
public class JogadorController {

    private JogadorRepository jogadorRepository;
    private JogadorService jogadorService;

    public JogadorController(JogadorRepository jogadorRepository, JogadorService jogadorService) {
        this.jogadorRepository = jogadorRepository;
        this.jogadorService = jogadorService;
    }

    @GetMapping
    public ModelAndView listJogadores() {
        List<Jogador> jogadores = this.jogadorRepository.findAll();

        ModelAndView mv = new ModelAndView("view/jogadores");
        mv.addObject("jogadores", jogadores);

        return mv;
    }

    @GetMapping("/new")
    public ModelAndView newJogadorForm(Jogador jogador) {
        return new ModelAndView("view/cadastro");
    }

    @PostMapping("/new")
    public ModelAndView registerJogador(@Valid Jogador jogador, BindingResult result, RedirectAttributes attributes) {
        jogadorService.getJogadorCodinome(jogador);

        if (result.hasErrors() || checkDuplicateEmail(jogador, result)) {
            return new ModelAndView("view/cadastro");
        }

        if (jogador.getCodinome() == null) {
            attributes.addFlashAttribute("failure",
                    "Não existem mais codinomes disponíveis para " + jogador.getGrupo());
            return new ModelAndView("redirect:/jogadores/new");
        }
        jogadorRepository.save(jogador);
        attributes.addFlashAttribute("success", "Jogador cadastrado com sucesso!");
        return new ModelAndView("redirect:/jogadores/new");
    }

    @GetMapping("/{id}/edit")
    public ModelAndView editJogadorForm(@PathVariable Long id, Jogador jogador, RedirectAttributes attributes) {
        Optional<Jogador> optional = jogadorRepository.findById(id);
        if (optional.isPresent()) {
            Jogador jogadorOp = optional.get();
            ModelAndView mv = new ModelAndView("view/editar");
            mv.addObject("jogador", jogadorOp);
            return mv;
        } else {
            attributes.addFlashAttribute("failure", "Jogador não encontrado");
            return new ModelAndView("redirect:/jogadores");
        }
    }

    @PostMapping("/{id}")
    public ModelAndView updateJogador(@PathVariable Long id, @Valid Jogador jogador, BindingResult result,
            RedirectAttributes attributes) {
        try {
            if (result.hasErrors()) {
                return new ModelAndView("view/editar");
            }
            Optional<Jogador> optional = jogadorRepository.findById(id);
            if (optional.isPresent()) {
                jogadorRepository.save(jogador);
                attributes.addFlashAttribute("success", "Jogador atualizado com sucesso!");
                return new ModelAndView("redirect:/jogadores");
            } else {
                attributes.addFlashAttribute("failure", "Ocorreu um erro ao atualizar este jogador");
                return new ModelAndView("redirect:/jogadores");
            }
        } catch (DataIntegrityViolationException ex) {
            attributes.addFlashAttribute("failure", "Email já existe");
            return new ModelAndView("redirect:/jogadores/" + id + "/edit");
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteJogador(@PathVariable Long id, RedirectAttributes attributes) {
        jogadorRepository.deleteById(id);
        attributes.addFlashAttribute("success", "Jogador excluído com sucesso!");
        return "redirect:/jogadores";
    }

    private boolean checkDuplicateEmail(@Valid Jogador jogador, BindingResult result) {
        if (jogadorRepository.findByEmail(jogador.getEmail()).isPresent()) {
            FieldError error = new FieldError("jogador", "email", "Email já existe");
            result.addError(error);
            return true;
        }
        return false;
    }

}
