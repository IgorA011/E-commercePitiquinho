package br.loja.pitiquinho.controller;

import br.loja.pitiquinho.model.Produto;
import br.loja.pitiquinho.service.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ListarProdutosController {

    private final ProdutoService produtoService;

    public ListarProdutosController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/adm/lista-produto")
    public String listarProdutos(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Produto> produtosPage;

        if (nome != null && !nome.isEmpty()) {
            produtosPage = produtoService.buscarPorNomePaginado(nome, pageable);
        } else {
            produtosPage = produtoService.listarTodosPaginado(pageable);
        }

        model.addAttribute("produtos", produtosPage.getContent());
        model.addAttribute("currentPage", produtosPage.getNumber());
        model.addAttribute("totalPages", produtosPage.getTotalPages());

        return "lista-produto-adm";
    }
}
