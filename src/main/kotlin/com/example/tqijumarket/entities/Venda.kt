package com.example.tqijumarket.entities

import com.example.tqijumarket.enums.FormaPagamento
import jakarta.persistence.*

@Entity
data class Venda(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)// -> ID gerado automaticamente
    var id: Long = 0,

    //Relacionamento um para muitos, venda pode está em vários carrinhos
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "venda") // -> está sendo mapeada como venda em carrinho.
    var itensVenda: List<Carrinho>,

    @Column(nullable = false)// -> não pode ser nula
    var precoTotal: Double,

    @Enumerated(EnumType.STRING) //-> Armazenado como uma String (ENUM para retornar um "status").
    @Column(nullable = false)// -> não pode ser nula
    var formaPagamento: FormaPagamento
)