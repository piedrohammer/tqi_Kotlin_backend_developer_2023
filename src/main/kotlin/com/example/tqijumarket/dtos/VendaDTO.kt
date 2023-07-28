package com.example.tqijumarket.dtos

import com.example.tqijumarket.enums.FormaPagamento

//(Data Transfer Object) -> transfêrencia de dados entre camadas de codigo para o melhor tratamento do código
data class VendaDTO(

    val precoTotal: Double,
    val formaPagamento: FormaPagamento

)