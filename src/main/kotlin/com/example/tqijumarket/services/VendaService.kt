package com.example.tqijumarket.services

import com.example.tqijumarket.dtos.VendaDTO
import com.example.tqijumarket.entities.Venda
import com.example.tqijumarket.exceptions.InvalidPriceException
import com.example.tqijumarket.repositories.CarrinhoRepository
import com.example.tqijumarket.repositories.VendaRepository
import org.springframework.stereotype.Service

@Service
class VendaService(
    private val carrinhoRepository: CarrinhoRepository,
    private val vendaRepository: VendaRepository
) {

    //Finalização da Venda
    fun finalizarVenda(vendaDTO: VendaDTO): String {
        val itensCarrinho = carrinhoRepository.findAll()

        //Logica para pegar todos os itens do carrinho e fazer a soma dos preços deles
        val precoTotal = itensCarrinho.sumOf { carrinho -> carrinho.precoVenda ?: 0.0 }

        //Lança uma exceção quando for finalizar a venda e informar o preço inválido.
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

        return "Venda finalizada com sucesso. Preço Total: R$ $precoTotal, Forma de Pagamento: ${vendaDTO.formaPagamento}."
    }
}