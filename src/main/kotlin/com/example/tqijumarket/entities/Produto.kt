package com.example.tqijumarket.entities

import jakarta.persistence.*

@Entity
data class Produto(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)// -> ID gerado automaticamente
    var id: Long = 0,
    var nome: String,
    var unidadeMedida: String,
    var precoUnitario: Double,

    //Relacionamento muitos para um, muitos produtos para uma categoria.
    @ManyToOne(fetch = FetchType.EAGER) // -> EAGER para carregar de imediato com o entidade pai
    @JoinColumn(name = "categoria_id") // -> Coluna q será usada como foreign key para o relacionamento.
    var categoria: Categoria

)