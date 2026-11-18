package br.fiap.breno562584.controller;

import br.fiap.breno562584.dto.BairroDTO;
import br.fiap.breno562584.service.BairroService;
import br.fiap.breno562584.service.PainelService;
import br.fiap.breno562584.service.RegraNegocioException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/bairros")
public class BairroController {
    private static final List<String> ZONAS =
            List.of("Centro", "Norte", "Sul", "Leste", "Oeste");

    private final BairroService service;
    private final PainelService painelService;

    public BairroController(BairroService service, PainelService painelService) {
        this.service = service;
        this.painelService = painelService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("bairros", service.listar());
        return "bairros/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("bairro", new BairroDTO());
        model.addAttribute("zonas", ZONAS);
        return "bairros/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes ra) {
        try {
            model.addAttribute("bairro", service.buscarPorId(id));
            model.addAttribute("zonas", ZONAS);
            return "bairros/form";
        } catch (RegraNegocioException e) {
            ra.addFlashAttribute("erro", e.getMessage());
            return "redirect:/bairros";
        }
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("bairro") BairroDTO dto,
                         BindingResult result, Model model, RedirectAttributes ra) {
        if (result.hasErrors()) {
            model.addAttribute("zonas", ZONAS);
            return "bairros/form";
        }
        try {
            service.salvar(dto);
            ra.addFlashAttribute("mensagem", "Bairro salvo com sucesso.");
        } catch (RegraNegocioException e) {
            ra.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/bairros";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        try {
            service.excluir(id);
            ra.addFlashAttribute("mensagem", "Bairro excluído com sucesso.");
        } catch (RegraNegocioException e) {
            ra.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/bairros";
    }

    @GetMapping("/{id}/painel")
    public String painel(@PathVariable Long id, Model model, RedirectAttributes ra) {
        try {
            model.addAttribute("painel", painelService.montarPainel(id));
            return "bairros/painel";
        } catch (RegraNegocioException e) {
            ra.addFlashAttribute("erro", e.getMessage());
            return "redirect:/bairros";
        }
    }
}