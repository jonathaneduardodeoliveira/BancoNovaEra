package com.banco.nova.era.controller

import com.banco.nova.era.dto.CadastroUsuarioDTO
import com.banco.nova.era.model.Endereco
import com.banco.nova.era.model.Usuario
import com.banco.nova.era.service.CadastroService
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(CadastroController::class)
class CadastroControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var cadastroService: CadastroService

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun `deve cadastrar usuario com sucesso`() {
        val dto = CadastroUsuarioDTO(
            nome = "Jonathan",
            cpf = "12345678900",
            senha = "123456",
            email = "jonathan@example.com",
            telefone = "11982399984",
            conta = 1234
        )

        val usuarioSalvo = Usuario(
            cpf = dto.cpf,
            nome = dto.nome,
            conta = dto.conta,
            senha = dto.senha,
            email = dto.email,
            telefone = dto.telefone,
            endereco = Endereco(
                cep = dto.endereco.cep,
                logradouro = dto.endereco.logradouro,
                bairro = dto.endereco.bairro,
                cidade = dto.endereco.cidade,
                uf = dto.endereco.uf,
                numero = dto.endereco.numero,
                complemento = dto.endereco.complemento
            ),
            valor = 0.0
        )

        Mockito.`when`(cadastroService.cadastrar(dto)).thenReturn(usuarioSalvo)

        val json = objectMapper.writeValueAsString(dto)

        mockMvc.perform(
            post("/cadastro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.cpf", `is`(dto.cpf)))
            .andExpect(jsonPath("$.nome", `is`(dto.nome)))
            .andExpect(jsonPath("$.email", `is`(dto.email)))
    }
}
