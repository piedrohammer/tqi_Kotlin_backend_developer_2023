package com.example.tqijumarket.services

import com.example.tqijumarket.dtos.VendaDTO
import com.example.tqijumarket.entities.Carrinho
import com.example.tqijumarket.entities.Categoria
import com.example.tqijumarket.entities.Produto
import com.example.tqijumarket.entities.Venda
import com.example.tqijumarket.enums.FormaPagamento
import com.example.tqijumarket.repositories.CarrinhoRepository
import com.example.tqijumarket.repositories.ProdutoRepository
import com.example.tqijumarket.repositories.VendaRepository
import org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.any
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.*
import org.mockito.Mockito.any
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.doReturn

import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class VendaServiceTest{

    @Mock
    private lateinit var carrinhoRepository: CarrinhoRepository

    @Mock
    private lateinit var vendaRepository: VendaRepository

    @Mock
    private lateinit var produtoRepository: ProdutoRepository

    @InjectMocks
    private lateinit var vendaService: VendaService


    @Test
    fun testFinalizarVenda_Success() {

        val vendaDTO = VendaDTO(precoTotal = 100.0, formaPagamento = FormaPagamento.CARTAO_CREDITO)

        //Cria os produtos para os carrinhos
        val produto1 = Produto(1L, "Produto 1", "unidade", 10.0, Categoria(1L, "Categoria 1"))
        val produto2 = Produto(2L, "Produto 2", "unidade", 20.0, Categoria(2L, "Categoria 2"))

        //Cria o carrinho
        val carrinho1 = Carrinho(produto = produto1, quantidade = 1, precoVenda = 50.0)
        val carrinho2 = Carrinho(produto = produto2, quantidade = 1, precoVenda = 50.0)
        val itensCarrinho = listOf(carrinho1, carrinho2)
        Mockito.`when`(carrinhoRepository.findAll()).thenReturn(itensCarrinho)


        //Teste
        val resultado = vendaService.finalizarVenda(vendaDTO)

        //Compara
        assertEquals("Venda finalizada com sucesso. Preço Total: R$ 100.0, Forma de Pagamento: CARTAO_CREDITO.", resultado)
    }



}