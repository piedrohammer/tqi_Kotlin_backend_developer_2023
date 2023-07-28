package com.example.tqijumarket.services

import com.example.tqijumarket.dtos.CategoriaDTO
import com.example.tqijumarket.entities.Categoria
import com.example.tqijumarket.exceptions.ExistException
import com.example.tqijumarket.repositories.CategoriaRepository
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class CategoriaServiceTest {

    @Mock
    private lateinit var categoriaRepository: CategoriaRepository

    @InjectMocks
    private lateinit var categoriaService: CategoriaService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test

    fun testCriarCategoria() {

        //Cria a categoria
        val categoriaDTO = CategoriaDTO("Nova Categoria")


        Mockito.`when`(categoriaRepository.save(Mockito.any(Categoria::class.java))).thenReturn(Categoria(1L, categoriaDTO.nome))

        //Teste
        val categoriaCriada = categoriaService.criarCategoria(categoriaDTO)

        //Verifica se é nula e a compara
        assertNotNull(categoriaCriada)
        assertEquals(categoriaDTO.nome, categoriaCriada.nome)
    }


    @Test
    fun testCriarCategoriaExistente() {

        //Cria a Categoria
        val categoriaDTO = CategoriaDTO("Categoria Existente")
        val categoriaExistente = Categoria(1L, "Categoria Existente")
        Mockito.`when`(categoriaRepository.findByNome(categoriaDTO.nome)).thenReturn(categoriaExistente)

        //Verifica se a exceção é lançada
        assertThrows(ExistException::class.java) {
            categoriaService.criarCategoria(categoriaDTO)
        }
    }

    @Test
    fun testListarCategorias() {

        //Crie uma lista de categorias
        val categorias = listOf(Categoria(1L, "Categoria 1"), Categoria(2L, "Categoria 2"))
        Mockito.`when`(categoriaRepository.findAll()).thenReturn(categorias)

        //Teste
        val listaDeCategorias = categoriaService.listarCategorias()

        //Compara e ve se é True/Verdadeira
        assertEquals(categorias.size, listaDeCategorias.size)
        assertTrue(listaDeCategorias.containsAll(categorias))
    }
    @Test
    fun testAtualizarCategoria() {

        //Crie a Categoria
        val id = 1L
        val categoriaDTO = CategoriaDTO("Categoria Atualizada")
        val categoriaExistente = Categoria(id, "Categoria Antiga")

        //Faz a o teste para 'quando' a categoria é criada
        Mockito.`when`(categoriaRepository.findById(id)).thenReturn(java.util.Optional.of(categoriaExistente))
        Mockito.`when`(categoriaRepository.save(categoriaExistente)).thenReturn(categoriaExistente)

        val categoriaAtualizada = categoriaService.atualizarCategoria(id, categoriaDTO)

        //Verifica se é nula e e compara
        assertNotNull(categoriaAtualizada)
        if (categoriaAtualizada != null) {
            assertEquals(id, categoriaAtualizada.id)
        }
        if (categoriaAtualizada != null) {
            assertEquals(categoriaDTO.nome, categoriaAtualizada.nome)
        }
    }

    @Test
    fun testExcluirCategoria() {

        //Crie a Categoria
        val id = 1L
        val categoriaExistente = Categoria(id, "Categoria Existente")

        //Faz a o teste para 'quando' a categoria é criada
        Mockito.`when`(categoriaRepository.findById(id)).thenReturn(java.util.Optional.of(categoriaExistente))

        //Teste
        categoriaService.excluirCategoria(id)

        //simulação do objeto
        Mockito.verify(categoriaRepository, Mockito.times(1)).delete(categoriaExistente)
    }
}

