package br.fiap.breno562584.controller;

import br.fiap.breno562584.dto.LeituraDTO;
import br.fiap.breno562584.service.LeituraService;
import br.fiap.breno562584.service.RegraNegocioException;
import br.fiap.breno562584.service.TrechoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/leituras")
public class LeituraController {
    private final LeituraService service;
    private final TrechoService trechoService;

    public LeituraController(LeituraService service, TrechoService trechoService) {
        this.service = service;
        this.trechoService = trechoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("leituras", service.listar());
        return "leituras/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("leitura", new LeituraDTO());
        model.addAttribute("trechos", trechoService.listar());
        return "leituras/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes ra) {
        try {
            model.addAttribute("leitura", service.buscarPorId(id));
            model.addAttribute("trechos", trechoService.listar());
            return "leituras/form";
        } catch (RegraNegocioException e) {
            ra.addFlashAttribute("erro", e.getMessage());
            return "redirect:/leituras";
        }
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("leitura") LeituraDTO dto,
                         BindingResult result, Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("trechos", trechoService.listar());
            return "leituras/form";
        }
        try {
            service.salvar(dto);
            ra.addFlashAttribute("mensagem", "Leitura salva com sucesso.");
        } catch (RegraNegocioException e) {
            ra.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/leituras";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        try {
            service.excluir(id);
            ra.addFlashAttribute("mensagem", "Leitura excluída com sucesso.");
        } catch (RegraNegocioException e) {
            ra.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/leituras";
    }
}
