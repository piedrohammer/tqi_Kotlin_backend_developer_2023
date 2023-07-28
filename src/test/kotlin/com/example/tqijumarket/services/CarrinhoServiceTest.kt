package com.example.tqijumarket.services

import com.example.tqijumarket.dtos.CarrinhoDTO
import com.example.tqijumarket.dtos.VendaDTO
import com.example.tqijumarket.entities.Carrinho
import com.example.tqijumarket.entities.Categoria
import com.example.tqijumarket.entities.Produto
import com.example.tqijumarket.enums.FormaPagamento
import com.example.tqijumarket.exceptions.NotExistException
import com.example.tqijumarket.repositories.CarrinhoRepository
import com.example.tqijumarket.repositories.ProdutoRepository
import com.example.tqijumarket.repositories.VendaRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class CarrinhoServiceTest{

    @Mock
    private lateinit var carrinhoRepository: CarrinhoRepository

    @Mock
    private lateinit var produtoRepository: ProdutoRepository

    @InjectMocks
    private lateinit var carrinhoService: CarrinhoService

    @Mock
    private lateinit var vendaRepository: VendaRepository



    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testAdicionarProdutosAoCarrinho() {
        // Cria o Produtos
        val produto1 = Produto(1L, "Produto 1", "unidade", 10.0, Categoria(1L, "Categoria 1"))
        val produto2 = Produto(2L, "Produto 2", "unidade", 20.0, Categoria(2L, "Categoria 2"))

        //Criando a lista do Carrinho
        val carrinhoDTOList = listOf(
            CarrinhoDTO(1L, 3, 30.0),
            CarrinhoDTO(2L, 2, 40.0)
        )

        //Comportamento do objeto
        Mockito.`when`(produtoRepository.findById(1L)).thenReturn(java.util.Optional.of(produto1))
        Mockito.`when`(produtoRepository.findById(2L)).thenReturn(java.util.Optional.of(produto2))
        Mockito.`when`(carrinhoRepository.saveAll(Mockito.anyList())).thenAnswer { it.arguments[0] as List<Carrinho> }

        //Teste
        val carrinhoList = carrinhoService.adicionarProdutosAoCarrinho(carrinhoDTOList)

        //Verifica se é nulo e o compara
        assertNotNull(carrinhoList)
        assertEquals(carrinhoDTOList.size, carrinhoList.size)
        assertEquals(produto1, carrinhoList[0].produto)
        assertEquals(produto2, carrinhoList[1].produto)
    }

    @Test
    fun testListarProdutosNoCarrinho() {

        //Cria o Carrinho
        val carrinho1 = Carrinho(1L, Produto(1L, "Produto 1", "unidade", 10.0, Categoria(1L, "Categoria 1")), 3, 30.0)
        val carrinho2 = Carrinho(2L, Produto(2L, "Produto 2", "unidade", 20.0, Categoria(2L, "Categoria 2")), 2, 40.0)
        val carrinhoList = listOf(carrinho1, carrinho2)

        //Comportamento do objeto
        Mockito.`when`(carrinhoRepository.findAll()).thenReturn(carrinhoList)

        //Teste
        val listaDeCarrinho = carrinhoService.listarProdutosNoCarrinho()

        //Compara e verifica se é True/Verdadeiro
        assertEquals(carrinhoList.size, listaDeCarrinho.size)
        assertTrue(listaDeCarrinho.containsAll(carrinhoList))
    }

    @Test
    fun testCalcularPrecoTotalProdutosNoCarrinho() {

        //Cria Carrinho
        val carrinho1 = Carrinho(1L, Produto(1L, "Produto 1", "unidade", 10.0, Categoria(1L, "Categoria 1")), 3, 30.0)
        val carrinho2 = Carrinho(2L, Produto(2L, "Produto 2", "unidade", 20.0, Categoria(2L, "Categoria 2")), 2, 40.0)
        val carrinhoList = listOf(carrinho1, carrinho2)

        //Comportamento do objeto
        Mockito.`when`(carrinhoRepository.findAll()).thenReturn(carrinhoList)

        //Teste
        val precoTotal = carrinhoService.calcularPrecoTotalProdutosNoCarrinho()

        //Compara
        assertEquals("170,00", precoTotal)
    }

    @Test
    fun testFinalizarVenda() {

        //Crie um objeto VendaDTO com os dados de teste
        val vendaDTO = VendaDTO(precoTotal = 100.0, formaPagamento = FormaPagamento.CARTAO_CREDITO)

        //Crie os produtos para os carrinhos
        val produto1 = Produto(1L, "Produto 1", "unidade", 10.0, Categoria(1L, "Categoria 1"))
        val produto2 = Produto(2L, "Produto 2", "unidade", 20.0, Categoria(2L, "Categoria 2"))

        //Cria o carrinho
        val carrinho1 = Carrinho(produto = produto1, quantidade = 1, precoVenda = 50.0)
        val carrinho2 = Carrinho(produto = produto2, quantidade = 1, precoVenda = 50.0)
        val itensCarrinho = listOf(carrinho1, carrinho2)
        //Comportamento do objeto
        `when`(carrinhoRepository.findAll()).thenReturn(itensCarrinho)

        //Testando
        val venda = carrinhoService.finalizarVenda(vendaDTO)

        //Compara
        assertEquals(100.0, venda.precoTotal)
        assertEquals(FormaPagamento.CARTAO_CREDITO, venda.formaPagamento)
        assertEquals(2, venda.itensVenda.size)
    }

    @Test
    fun testExcluirCarrinhoCarrinhoExistente() {

        //Cria Carrinho
        val carrinhoId = 1L

        //Comportamento do objeto
        Mockito.`when`(carrinhoRepository.existsById(carrinhoId)).thenReturn(true)

        //Teste
        carrinhoService.excluirCarrinho(carrinhoId)

        //Simulação do Objeto
        Mockito.verify(carrinhoRepository, Mockito.times(1)).deleteById(carrinhoId)
    }

    @Test
    fun testExcluirCarrinhoCarrinhoNaoExistente() {

        //Cria carrinho
        val carrinhoId = 1L

        //Comportamento do objeto
        Mockito.`when`(carrinhoRepository.existsById(carrinhoId)).thenReturn(false)

        //Verifica a exceção
        assertThrows(NotExistException::class.java) {
            carrinhoService.excluirCarrinho(carrinhoId)
        }

        //Simulação do Objeto
        Mockito.verify(carrinhoRepository, Mockito.never()).deleteById(carrinhoId)
    }
}
