package com.oficinadobrito.api.Products;

import com.oficinadobrito.api.Controllers.Dtos.Creates.ProdutoCreateDto;
import com.oficinadobrito.api.Controllers.Dtos.Updates.ProdutoUpdateDto;
import com.oficinadobrito.api.Entities.Produto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.Field;
import java.util.Set;

@ActiveProfiles("test")
@SpringBootTest
class ProductModelTests {

    private Produto produto;

    @BeforeEach
    void setUp() {
        produto = new Produto();
    }

    @DisplayName("When a produto is created, atribute nome must exist")
    @Test
    void testWhenAProduto_IsCreatedTheProductsAttribute_NameMustExist() throws NoSuchFieldException {
        // Given - Arrange
        Field nome = Produto.class.getDeclaredField("nome");

        // When  - Act
        this.produto.setNome("A");

        // Then  - Assert
        Assertions.assertNotNull(nome, () -> "The nome attribute does not exist in model product");
        Assertions.assertEquals(String.class, nome.getType(), () -> "The type of the product nome attribute must be one String");
        Assertions.assertEquals("A", this.produto.getNome().toUpperCase(), () -> "Error in methods setNome or getNome");
    }

    @DisplayName("When a produto is created, attribute produtoId must exist")
    @Test
    void testWhenAProduto_IsCreatedTheProductsAttributeProdutoId_MustExist() throws NoSuchFieldException {
        // Given - Arrange
        Field produtoId = Produto.class.getDeclaredField("produtoId");

        // When  - Act
        produto.setProdutoId(1);

        // Then  - Assert
        Assertions.assertNotNull(produtoId, () -> "The produtoId attribute does not exist in model product");
        Assertions.assertEquals(long.class, produtoId.getType(), () -> "The type of the product produtoId attribute must be one long");
    }

    @DisplayName("When a produto is created, attributes unidadeMedida must exist")
    @Test
    void testWhenAProduto_IsCreatedTheProductsAttributeUnidadeMedida_MustExist() throws NoSuchFieldException {
        // Given - Arrange
        Field unidadeMedida = Produto.class.getDeclaredField("unidadeMedida");

        // When  - Act
        this.produto.setUnidadeMedida("Kg");

        // Then  - Assert
        Assertions.assertNotNull(unidadeMedida, () -> "The unidadeMedida attribute does not exist in model product");
        Assertions.assertEquals(String.class, unidadeMedida.getType(), () -> "The type of the product unidadeMedida attribute must be one String");
    }

    @DisplayName("When a produto is created, attribute preco must exist")
    @Test
    void testWhenAProduto_IsCreatedTheProductsAttributePreco_MustExist() throws NoSuchFieldException {
        // Given - Arrange
        Field preco = Produto.class.getDeclaredField("preco");

        // When  - Act
        this.produto.setPreco(3.50);

        // Then  - Assert
        Assertions.assertNotNull(preco, () -> "The preco attribute does not exist in model product");
        Assertions.assertEquals(double.class, preco.getType(), () -> "The type of the product preco attribute must be one double");
    }
    @DisplayName("When a produto is created,attribute PrecisaManutencao must exist")
    @Test
    void testWhenAProduto_IsCreatedTheProductsAttributePrecisaManutencao_MustExist() throws NoSuchFieldException {
        // Given - Arrange
        Field precisaManutencao = Produto.class.getDeclaredField("precisaManutencao");

        // When  - Act
        this.produto.setPrecisaManutencao(true);

        // Then  - Assert
        Assertions.assertNotNull(precisaManutencao, () -> "The precisaManutencao attribute does not exist in model product");
        Assertions.assertEquals(boolean.class, precisaManutencao.getType(), () -> "The type of the product precisaManutencao attribute must be one boolean");
    }
    @DisplayName("When a produto is created, attribute quantidadeMesesAteManutencao must exist")
    @Test
    void testWhenAProduto_IsCreatedTheProductsAttributeQuantidadeMesesAteManutencao_MustExist() throws NoSuchFieldException {
        // Given - Arrange
        Field quantidadeMesesAteManutencao = Produto.class.getDeclaredField("quantidadeMesesAteManutencao");

        // When  - Act
        this.produto.setQuantidadeMesesAteManutencao(3);

        // Then  - Assert
        Assertions.assertNotNull(quantidadeMesesAteManutencao, () -> "The quantidadeMesesAteManutencao attribute does not exist in model product");
        Assertions.assertEquals(int.class, quantidadeMesesAteManutencao.getType(), () -> "The type of the product quantidadeMesesAteManutencao attribute must be one int");
    }
    @DisplayName("When a produto is created, attribute vendedores must exist")
    @Test
    void testWhenAProduto_IsCreatedTheProductsAttributeVendedores_MustExist() throws NoSuchFieldException {
        // Given - Arrange
        Field vendedores = Produto.class.getDeclaredField("vendedores");

        // When  - Act
        this.produto.setVendedores(null);

        // Then  - Assert
        Assertions.assertNotNull(vendedores, () -> "The vendedores attribute does not exist in model product");
        Assertions.assertEquals(Set.class, vendedores.getType(), () -> "The type of the product vendedores attribute must be one Set<Vendedor>");

    }
    @DisplayName("When a produto is created,attribute recolhimentos must exist")
    @Test
    void testWhenAProduto_IsCreatedTheProductsAttributeRecolhimentos_MustExist() throws NoSuchFieldException {
        // Given - Arrange
        Field recolhimentos = Produto.class.getDeclaredField("recolhimentos");

        // When  - Act
        this.produto.setRecolhimentos(null);
        //Then - Assert
        Assertions.assertNotNull(recolhimentos, () -> "The recolhimentos attribute does not exist in model product");
        Assertions.assertEquals(Set.class, recolhimentos.getType(), () -> "The type of the product recolhimentos attribute must be one List<Recolhimento>");
    }

    @DisplayName("Test Whe Method ToEntity Return Success")
    @Test
    void testWheToEntity_Success(){
        // Given - Arrange
        ProdutoCreateDto dto = new ProdutoCreateDto();
        dto.setNome("Produto Teste");
        dto.setUnidadeMedida("kg");
        dto.setPreco(10.99);

        // When  - Act
        Produto produtocr = Produto.toEntity(dto);

        //Then - Assert
        Assertions.assertNotNull(produtocr);
        Assertions.assertEquals("Produto Teste", produtocr.getNome());
        Assertions.assertEquals("kg", produtocr.getUnidadeMedida());
        Assertions.assertEquals(10.99, produtocr.getPreco());
    }

    @DisplayName("Test Whe Method With Update Dto ToEntity Return One Produto")
    @Test
    void testWheToEntityWith_Update_Success(){
        // Given - Arrange
        ProdutoUpdateDto dto = new ProdutoUpdateDto();
        dto.setNome("Produto Modificado");
        dto.setUnidadeMedida("kg");
        dto.setPreco(10.99);

        // When  - Act
        Produto produtoAtt = Produto.toEntity(dto);

        //Then - Assert
        Assertions.assertNotNull(produtoAtt);
        Assertions.assertEquals("Produto Modificado", produtoAtt.getNome());
        Assertions.assertEquals("kg", produtoAtt.getUnidadeMedida());
        Assertions.assertEquals(10.99, produtoAtt.getPreco());
    }
}
