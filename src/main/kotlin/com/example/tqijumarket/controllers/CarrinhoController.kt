package com.example.tqijumarket.controllers

import com.example.tqijumarket.dtos.CarrinhoDTO
import com.example.tqijumarket.dtos.VendaDTO
import com.example.tqijumarket.entities.Carrinho
import com.example.tqijumarket.exceptions.InvalidPriceException
import com.example.tqijumarket.services.CarrinhoService
import com.example.tqijumarket.services.VendaService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/carrinho")// caminho/path/endipoint -> Ex: http://localhost:8080/carrinho/
class CarrinhoController(private val carrinhoService: CarrinhoService,  private val vendaService: VendaService) {

    //Requisição POST para a adicionar Produtos no Carrinho
    @PostMapping
    fun adicionarProdutosAoCarrinho(@Valid @RequestBody carrinhoDTOList: List<CarrinhoDTO>): ResponseEntity<List<Carrinho>> { //Validação e capturação de dados do corpo.
        val carrinhoList = carrinhoService.adicionarProdutosAoCarrinho(carrinhoDTOList)                                       //ResponseEntity para retornar uma resposta HTTP
        return ResponseEntity.status(HttpStatus.CREATED).body(carrinhoList)
    }

    //Requisição GET de Lista todos os Produtos no carrinho
    @GetMapping
    fun listarProdutosNoCarrinho(): ResponseEntity<List<Carrinho>> { //ResponseEntity para retornar uma resposta HTTP
        val produtosNoCarrinho = carrinhoService.listarProdutosNoCarrinho()
        return ResponseEntity.ok(produtosNoCarrinho)
    }

    //Requisição GET de Calcular o preço total de produtos no carrinho
    @GetMapping("/preco-total")// Caminho para calcular o preço total Ex: http://localhost:8080/carrinho/preco-total
    fun calcularPrecoTotalProdutosNoCarrinho(): ResponseEntity<String> {//ResponseEntity para retornar uma resposta HTTP
        val precoTotal = carrinhoService.calcularPrecoTotalProdutosNoCarrinho()
        return ResponseEntity.ok("Preço Total: $precoTotal")
    }



    //Requisição POST de finalizar venda
    @PostMapping("/finalizar-venda")// Caminho para finalizar a venda Ex: http://localhost:8080/carrinho/finalizar-venda
    fun finalizarVenda(@RequestBody vendaDTO: VendaDTO): ResponseEntity<String> { //Validação e capturação de dados do corpo.
                                                                                  //ResponseEntity para retornar uma resposta HTTP

        //Faz uma exceção Tenta finalizar a venda se estiver certo finaliza a venda e retorna a mensagem de sucesso
        //Se Estiver o preço errado retorna a mensagem de preço total invalido.
        //Se a venda estiver algo errado além disso retorna o erro ao finalizar a venda.
        try {
            val venda = carrinhoService.finalizarVenda(vendaDTO)
            return ResponseEntity.ok("Venda finalizada com sucesso. Forma de Pagamento: ${vendaDTO.formaPagamento}")
        } catch (ex: InvalidPriceException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao finalizar a venda: Preço total inválido.")
        } catch (ex: Exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao finalizar a venda.")
        }
    }


    //Requisição DEL para exluir produto do carrinho
    @DeleteMapping("/{carrinhoId}")// Caminho para deletar produto do carrinho Ex: http://localhost:8080/carrinho/1
    fun excluirCarrinho(@PathVariable carrinhoId: Long): ResponseEntity<String> {//ResponseEntity para retornar uma resposta HTTP
        carrinhoService.excluirCarrinho(carrinhoId)
        return ResponseEntity.ok("Item excluído com sucesso.")
    }
}