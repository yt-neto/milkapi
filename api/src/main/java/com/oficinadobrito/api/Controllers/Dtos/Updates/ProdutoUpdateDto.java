package com.oficinadobrito.api.Controllers.Dtos.Updates;


import lombok.Data;

@Data
public class ProdutoUpdateDto {
    private String nome;
    private String unidadeMedida;
    private double preco;
}
