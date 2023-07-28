package com.example.tqijumarket.entities

import jakarta.persistence.*

@Entity
data class Carrinho(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)// -> ID gerado automaticamente
    var id: Long = 0,

    //Relacionamento muitos para um, muitos produtos para um carrinho.
    @ManyToOne(fetch = FetchType.EAGER)// -> EAGER para carregar de imediato com o entidade pai
    @JoinColumn(name = "produto_id")// -> Coluna q será usada como foreign key para o relacionamento.
    val produto: Produto,

    val quantidade: Int,
    var precoVenda: Double? = null,

    @ManyToOne
    var venda: Venda? = null

)


