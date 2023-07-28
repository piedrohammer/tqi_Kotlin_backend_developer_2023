package com.example.tqijumarket.services

import com.example.tqijumarket.dtos.ProdutoDTO
import com.example.tqijumarket.entities.Categoria
import com.example.tqijumarket.entities.Produto
import com.example.tqijumarket.repositories.ProdutoRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ProdutoServiceTest{

    @Mock
    private lateinit var produtoRepository: ProdutoRepository

    @InjectMocks
    private lateinit var produtoService: ProdutoService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testCriarProduto() {

        //Cria a categoria e produto
        val categoria = Categoria(1L, "Categoria Teste")
        val produtoDTO = ProdutoDTO("Produto Teste", "unidade", 10.0, categoria, 0L)

        Mockito.`when`(produtoRepository.save(Mockito.any(Produto::class.java))).thenAnswer { it.arguments[0] as Produto }

        //Teste
        val produtoCriado = produtoService.criarProduto(produtoDTO)

        //Verifica se é nulo e a compara
        assertNotNull(produtoCriado)
        assertEquals(produtoDTO.nome, produtoCriado.nome)
        assertEquals(produtoDTO.unidadeMedida, produtoCriado.unidadeMedida)
        assertEquals(produtoDTO.precoUnitario, produtoCriado.precoUnitario)
        assertEquals(categoria, produtoCriado.categoria)
    }


    @Test
    fun testListarProdutos() {

        //Cria uma lista de produtos
        val produtos = listOf(
            Produto(1L, "Produto 1", "unidade", 10.0, Categoria(1L, "Categoria 1")),
            Produto(2L, "Produto 2", "unidade", 20.0, Categoria(2L, "Categoria 2"))
        )

        Mockito.`when`(produtoRepository.findAll()).thenReturn(produtos)

        //Teste
        val listaDeProdutos = produtoService.listarProdutos()

        //Compara e verifica se é True/Verdadeira
        assertEquals(produtos.size, listaDeProdutos.size)
        assertTrue(listaDeProdutos.containsAll(produtos))
    }

    @Test
    fun testBuscarProdutoPorId() {

        //Cria Produto
        val id = 1L
        val produto = Produto(id, "Produto 1", "unidade", 10.0, Categoria(1L, "Categoria 1"))

        Mockito.`when`(produtoRepository.findById(id)).thenReturn(java.util.Optional.of(produto))

        //Teste
        val produtoEncontrado = produtoService.buscarProdutoPorId(id)

        //Verifica se é nulo e o compara
        assertNotNull(produtoEncontrado)
        assertEquals(id, produtoEncontrado.id)
        assertEquals(produto.nome, produtoEncontrado.nome)
    }

    @Test
    fun testAtualizarProduto() {

        //Cria produto
        val id = 1L
        val produtoDTO = ProdutoDTO("Produto Atualizado", "kg", 15.0, Categoria(2L, "Categoria 2"), id)
        val produtoExistente = Produto(id, "Produto Antigo", "unidade", 10.0, Categoria(1L, "Categoria 1"))

        Mockito.`when`(produtoRepository.findById(id)).thenReturn(java.util.Optional.of(produtoExistente))
        Mockito.`when`(produtoRepository.save(Mockito.any(Produto::class.java))).thenAnswer { it.arguments[0] as Produto }

        //Teste
        val produtoAtualizado = produtoService.atualizarProduto(id, produtoDTO)

        //Verifica se é nulo e o compara
        assertNotNull(produtoAtualizado)
        assertEquals(id, produtoAtualizado.id)
        assertEquals(produtoDTO.nome, produtoAtualizado.nome)
        assertEquals(produtoDTO.unidadeMedida, produtoAtualizado.unidadeMedida)
        assertEquals(produtoDTO.precoUnitario, produtoAtualizado.precoUnitario)
        assertEquals(produtoDTO.categoria, produtoAtualizado.categoria)
    }

    @Test
    fun testExcluirProduto() {
        //Cria produto
        val id = 1L
        val produtoExistente = Produto(id, "Produto 1", "unidade", 10.0, Categoria(1L, "Categoria 1"))

        Mockito.`when`(produtoRepository.findById(id)).thenReturn(java.util.Optional.of(produtoExistente))

        //Teste
        produtoService.excluirProduto(id)

        //simulação do objeto
        Mockito.verify(produtoRepository, Mockito.times(1)).delete(produtoExistente)
    }
}
