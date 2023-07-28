package com.example.tqijumarket.services

import com.example.tqijumarket.dtos.ProdutoDTO
import com.example.tqijumarket.entities.Produto
import com.example.tqijumarket.exceptions.NotExistException
import com.example.tqijumarket.repositories.ProdutoRepository
import org.springframework.stereotype.Service

@Service
class ProdutoService(private val produtoRepository: ProdutoRepository) {

    //Cria um produto. !! Acho que não precise de uma exceção, pois pode ter vários produtos diferentes com os mesmo atributos.
    fun criarProduto(produtoDTO: ProdutoDTO): Produto {
        val produto = Produto(
            nome = produtoDTO.nome,
            unidadeMedida = produtoDTO.unidadeMedida,
            precoUnitario = produtoDTO.precoUnitario,
            categoria = produtoDTO.categoria
        )
        return produtoRepository.save(produto)
    }

    //Lista todos os Produtos criados.
    fun listarProdutos(): List<Produto> {
        return produtoRepository.findAll()
    }

    //Busca um produto pelo ID
    fun buscarProdutoPorId(id: Long): Produto {

        //Lança uma exceção caso não exista um produto com o ID informado, se existe busca ele.
        return produtoRepository.findById(id)
            .orElseThrow { NotExistException("Produto com ID $id não encontrado.") }
    }

    //Atualiza um produto pelo ID
    fun atualizarProduto(id: Long, produtoDTO: ProdutoDTO): Produto {

        //Lança uma exceção caso não exista um produto com o ID informado, se existe atualiza ele.
        val produtoExistente = produtoRepository.findById(id)
            .orElseThrow { NotExistException("Produto não encontrado.") }

        produtoExistente.nome = produtoDTO.nome
        produtoExistente.unidadeMedida = produtoDTO.unidadeMedida
        produtoExistente.precoUnitario = produtoDTO.precoUnitario
        produtoExistente.categoria = produtoDTO.categoria

        return produtoRepository.save(produtoExistente)
    }


    //Exclui um produto pelo ID
    fun excluirProduto(id: Long) {

        //Lança uma exceção caso não exista um produto com o ID informado, se existe exclui ele.
        val produtoExistente = produtoRepository.findById(id)
            .orElseThrow { NotExistException("Produto não encontrado.") }

        produtoRepository.delete(produtoExistente)
    }
}