package com.example.tqijumarket.services

import com.example.tqijumarket.dtos.CarrinhoDTO
import com.example.tqijumarket.dtos.VendaDTO
import com.example.tqijumarket.entities.Carrinho
import com.example.tqijumarket.entities.Venda
import com.example.tqijumarket.exceptions.NotExistException
import com.example.tqijumarket.exceptions.InvalidPriceException
import com.example.tqijumarket.repositories.CarrinhoRepository
import com.example.tqijumarket.repositories.ProdutoRepository
import com.example.tqijumarket.repositories.VendaRepository
import org.springframework.stereotype.Service

@Service
class CarrinhoService(
    private val carrinhoRepository: CarrinhoRepository,
    private val produtoRepository: ProdutoRepository,
    private val vendaRepository: VendaRepository
) {

    //Adiciona Produtos no Carrinho
    fun adicionarProdutosAoCarrinho(carrinhoDTOList: List<CarrinhoDTO>): List<Carrinho> {

        val carrinhoList = mutableListOf<Carrinho>()//Criando uma lista que poder ser modificada.

        //Percorre a lista fazendo uma busca de produtos pelos ID deles e lança uma exceção caso não existe o produto com o ID informado.
        for (carrinhoDTO in carrinhoDTOList) {
            val produto = produtoRepository.findById(carrinhoDTO.produtoId)
                .orElseThrow { NotExistException("Produto com ID ${carrinhoDTO.produtoId} não encontrado.") }

            //Logica para calcular o preço de um produto multiplicando a quantidade pelo preço do produto.
            val precoVenda = produto.precoUnitario * carrinhoDTO.quantidade

            val carrinho = Carrinho(
                produto = produto,
                quantidade = carrinhoDTO.quantidade,
                precoVenda = precoVenda
            )

            carrinhoList.add(carrinho)
        }

        carrinhoRepository.saveAll(carrinhoList)

        return carrinhoList
    }

    //Lista todos os Produtos do Carrinho
    fun listarProdutosNoCarrinho(): List<Carrinho> {
        return carrinhoRepository.findAll()
    }

    //Calcula o preço total de Produtos do Carrinho
    fun calcularPrecoTotalProdutosNoCarrinho(): String {

        val carrinhoList = carrinhoRepository.findAll()

        //Logica para calcular o preço total
        val precoTotal = carrinhoList.sumOf { carrinho ->
            (carrinho.precoVenda ?: 0.0) * carrinho.quantidade
        }
        return String.format("%.2f", precoTotal) // -> Formatação em até 2 casas decimais Ex: 00,00
    }


    //Finalização da Venda
    fun finalizarVenda(vendaDTO: VendaDTO): Venda {
        val itensCarrinho = carrinhoRepository.findAll()

        //Logica do preço total do carrinho
        val precoTotal = itensCarrinho.sumOf { carrinho -> carrinho.precoVenda ?: 0.0 }

        //Lança uma exceção caso o preço total não seja o preço 'correto' informado.
        if (precoTotal != vendaDTO.precoTotal) {
            throw InvalidPriceException("Preço total inválido.")
        }

        val venda = Venda(
            precoTotal = precoTotal,
            formaPagamento = vendaDTO.formaPagamento,
            itensVenda = itensCarrinho
        )

        vendaRepository.save(venda)

        carrinhoRepository.deleteAll()

        return venda
    }

    //Excluir um Item no Carrinho
    fun excluirCarrinho(carrinhoId: Long) {

        //Lança uma exceção caso não existe um item do carrinho com o ID informado.
        if (carrinhoRepository.existsById(carrinhoId)) {
            carrinhoRepository.deleteById(carrinhoId)
        } else {
            throw NotExistException("Carrinho não encontrado.")
        }
    }
}