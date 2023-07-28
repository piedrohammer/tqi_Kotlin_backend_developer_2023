package com.example.tqijumarket.controllers

import com.example.tqijumarket.dtos.ProdutoDTO
import com.example.tqijumarket.entities.Produto
import com.example.tqijumarket.services.ProdutoService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/produtos")// caminho/path/endipoint -> Ex: http://localhost:8080/produtos/
class ProdutoController(private val produtoService: ProdutoService) {

    //Requisição POST para a função criar Produto
    @PostMapping
    fun criarProduto(@Valid @RequestBody produtoDTO: ProdutoDTO): ResponseEntity<Produto> { //Validação e capturação de dados do corpo.
        val novoProduto = produtoService.criarProduto(produtoDTO)                           //ResponseEntity para retornar uma resposta HTTP
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto)
    }

    //Requisição GET de Lista todos os Produtos
    @GetMapping
    fun listarProdutos(): ResponseEntity<List<Produto>> { //ResponseEntity para retornar uma resposta HTTP
        val produtos = produtoService.listarProdutos()
        return ResponseEntity.ok(produtos)
    }

    //Requisição GET de Busca um Produto pelo ID
    @GetMapping("/{id}") //Caminho para buscar um produto pelo ID Ex: http://localhost:8080/produtos/1
    fun buscarProdutoPorId(@PathVariable id: Long): ResponseEntity<Produto> {
        val produto = produtoService.buscarProdutoPorId(id)
        return ResponseEntity.ok(produto)
    }

    //Requisição PUT de Atualizar um Produto pelo ID
    @PutMapping("/{id}") //Caminho para atualizar um produto pelo ID Ex: http://localhost:8080/produtos/1
    fun atualizarProduto(
        @PathVariable id: Long,
        @RequestBody produtoDTO: ProdutoDTO
    ): ResponseEntity<Produto> { //ResponseEntity para retornar uma resposta HTTP
        val produtoAtualizado = produtoService.atualizarProduto(id, produtoDTO)
        return ResponseEntity.ok(produtoAtualizado)
    }

    //Requisição DEL de Excluir um Produto pelo ID
    @DeleteMapping("/{id}") // Caminho para excluir um produto pelo ID Ex: http://localhost:8080/produtos/1
    fun excluirProduto(@PathVariable id: Long): ResponseEntity<String> {
        produtoService.excluirProduto(id)
        return ResponseEntity.ok("Produto foi deletado com sucesso.")
    }
}