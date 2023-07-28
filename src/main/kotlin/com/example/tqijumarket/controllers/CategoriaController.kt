package com.example.tqijumarket.controllers

import com.example.tqijumarket.dtos.CategoriaDTO
import com.example.tqijumarket.entities.Categoria
import com.example.tqijumarket.exceptions.ExistException
import com.example.tqijumarket.repositories.CategoriaRepository
import com.example.tqijumarket.services.CategoriaService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/categorias")// caminho/path/endpoit -> http://localhost:8080/categorias
class CategoriaController(private val categoriaService: CategoriaService, private val categoriaRepository: CategoriaRepository) {

    //Requisição POST para a função criar categoria
    @PostMapping
    fun criarCategoria(@Valid @RequestBody categoriaDTO: CategoriaDTO): Categoria  { // validação e capturação de dados do corpo.
        val categoriaExistente = categoriaRepository.findByNome(categoriaDTO.nome)

        //Lança a exeção caso já existe uma categoria com o nome informado.
        if (categoriaExistente != null) {
            throw ExistException("Categoria '${categoriaDTO.nome}' já existe.")
        }

        val novaCategoria = Categoria(nome = categoriaDTO.nome)
        return categoriaRepository.save(novaCategoria)
    }

    //Requisição GET de Lista todas as Categorias
    @GetMapping
    fun listarCategorias(): ResponseEntity<List<Categoria>> { // -> ResponseEntity para retornar uma resposta HTTP
        val categorias = categoriaService.listarCategorias()
        return ResponseEntity.ok(categorias)
    }

    //Requisição GET de Busca uma Categoria pelo ID
    @GetMapping("/{id}")// caminho para buscar uma categoria pelo ID  Ex: http://localhost:8080/categorias/1
    fun buscarCategoriaPorId(@Valid @RequestBody @PathVariable id: Long): ResponseEntity<Categoria> { // -> ResponseEntity para retornar uma resposta HTTP
        val categoria = categoriaService.buscarCategoriaPorId(id)

        //Faz uma verificação para ver se existe uma categoria com esse ID
        return if (categoria != null) {
            ResponseEntity.ok(categoria)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    //Requisição PUT de Atualizar uma Categoria pelo ID
    @PutMapping("/{id}") // caminho para atualizar uma categoria pelo ID  Ex: http://localhost:8080/categorias/1
    fun atualizarCategoria(@Valid @PathVariable id: Long,
        @RequestBody categoriaDTO: CategoriaDTO
    ): ResponseEntity<Categoria> { // -> ResponseEntity para retornar uma resposta HTTP

        val categoriaAtualizada = categoriaService.atualizarCategoria(id, categoriaDTO)

        //Faz uma verificação para ver se existe uma categoria com esse ID
        return if (categoriaAtualizada != null) {
            ResponseEntity.ok(categoriaAtualizada)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    //Requisição DEL de Excluir uma Categoria pelo ID
    @DeleteMapping("/{id}") // caminho para excluir uma categoria pelo ID  Ex: http://localhost:8080/categorias/1
    fun excluirCategoria(@PathVariable id: Long): ResponseEntity<String> {  // -> ResponseEntity para retornar uma resposta HTTP
        categoriaService.excluirCategoria(id)
        return ResponseEntity.ok("Categoria foi deletada com sucesso.")
    }
}
