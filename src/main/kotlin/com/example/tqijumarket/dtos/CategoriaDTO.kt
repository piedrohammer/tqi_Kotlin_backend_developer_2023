package com.example.tqijumarket.dtos

import jakarta.validation.constraints.NotBlank

//(Data Transfer Object) -> transfêrencia de dados entre camadas de codigo para o melhor tratamento do código
data class CategoriaDTO(

    @NotBlank
    val nome: String

)
