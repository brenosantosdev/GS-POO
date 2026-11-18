package br.fiap.breno562584.controller;

import br.fiap.breno562584.dto.TrechoDTO;
import br.fiap.breno562584.service.BairroService;
import br.fiap.breno562584.service.RegraNegocioException;
import br.fiap.breno562584.service.TrechoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/trechos")
public class TrechoController {
    private static final List<String> TIPOS_CALCADA =
            List.of("Regular", "Irregular", "Escadaria", "Sem iluminação");

    private final TrechoService service;
    private final BairroService bairroService;

    public TrechoController(TrechoService service, BairroService bairroService) {
        this.service = service;
        this.bairroService = bairroService;
    }

    private void carregarListas(Model model) {
        model.addAttribute("bairros", bairroService.listar());
        model.addAttribute("tiposCalcada", TIPOS_CALCADA);
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("trechos", service.listar());
        return "trechos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("trecho", new TrechoDTO());
        carregarListas(model);
        return "trechos/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes ra) {
        try {
            model.addAttribute("trecho", service.buscarPorId(id));
            carregarListas(model);
            return "trechos/form";
        } catch (RegraNegocioException e) {
            ra.addFlashAttribute("erro", e.getMessage());
            return "redirect:/trechos";
        }
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("trecho") TrechoDTO dto,
                         BindingResult result, Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            carregarListas(model);
            return "trechos/form";
        }
        try {
            service.salvar(dto);
            ra.addFlashAttribute("mensagem", "Trecho salvo com sucesso.");
        } catch (RegraNegocioException e) {
            ra.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/trechos";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        try {
            service.excluir(id);
            ra.addFlashAttribute("mensagem", "Trecho excluído com sucesso.");
        } catch (RegraNegocioException e) {
            ra.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/trechos";
    }
}