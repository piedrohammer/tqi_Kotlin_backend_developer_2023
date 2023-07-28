package com.example.tqijumarket.services

import com.example.tqijumarket.dtos.CategoriaDTO
import com.example.tqijumarket.entities.Categoria
import com.example.tqijumarket.exceptions.ExistException
import com.example.tqijumarket.exceptions.NotExistException
import com.example.tqijumarket.repositories.CategoriaRepository
import org.springframework.stereotype.Service

@Service
class CategoriaService(private val categoriaRepository: CategoriaRepository) {


    //Cria uma Categoria
    fun criarCategoria(categoriaDTO: CategoriaDTO): Categoria {
        val categoriaExistente = categoriaRepository.findByNome(categoriaDTO.nome)

        //Faz uma verificação se existe a categoria pelo nome, se existe lança a exceção e se não existe cria uma.
        if (categoriaExistente != null) {
            throw ExistException("Categoria '${categoriaDTO.nome}' já existe.")
        }

        val novaCategoria = Categoria(nome = categoriaDTO.nome)
        return categoriaRepository.save(novaCategoria)
    }

    //Lista todas as Categorias criadas
    fun listarCategorias(): List<Categoria> {
        return categoriaRepository.findAll()
    }

    //Busca uma Categoria pelo ID
    fun buscarCategoriaPorId(id: Long): Categoria {

        //Lança uma exceção caso não existe uma Categoria com o ID informado.
        return categoriaRepository.findById(id).orElseThrow {
            NotExistException("Categoria com ID $id não encontrada.")
        }
    }

    //Atualiza uma Categoria pelo ID
    fun atualizarCategoria(id: Long, categoriaDTO: CategoriaDTO): Categoria? {

        //Lança uma exceção caso não existe uma Categoria com o ID informado, caso existe atualiza a categoria.
        val categoriaExistente = categoriaRepository.findById(id).orElseThrow {
            NotExistException("Categoria não encontrada.")
        }

        categoriaExistente.nome = categoriaDTO.nome
        return categoriaRepository.save(categoriaExistente)
    }

    //Exclui uma Categoria pelo ID.
    fun excluirCategoria(id: Long) {

        //Lança uma exceção caso não existe uma Categoria com o ID informado, caso existe exclui a categoria.
        val categoriaExistente = categoriaRepository.findById(id).orElseThrow {
            NotExistException("Categoria não encontrada.")
        }

        categoriaRepository.delete(categoriaExistente)
    }
}