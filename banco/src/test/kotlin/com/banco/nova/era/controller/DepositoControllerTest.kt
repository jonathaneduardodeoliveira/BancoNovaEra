@file:Suppress("removal", "DEPRECATION")

package com.banco.nova.era.controller

import com.banco.nova.era.dto.DepositoDTO
import com.banco.nova.era.service.DepositoService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import com.fasterxml.jackson.databind.ObjectMapper

@WebMvcTest(DepositoController::class)
class DepositoControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Suppress("DEPRECATION")
    @MockBean
    lateinit var depositoService: DepositoService

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Test
    fun `deve retornar sucesso quando deposito for permitido`() {
        val dto = DepositoDTO(cpf = "12345678900", valor = 100.0)

        `when`(depositoService.verificar(dto)).thenReturn(true)

        val json = objectMapper.writeValueAsString(dto)

        mockMvc.perform(
            post("/deposito")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(status().isOk)
            .andExpect(content().string("Deposito realizado com sucesso!"))
    }

    @Test
    fun `deve retornar erro 401 quando deposito for negado`() {
        val dto = DepositoDTO(cpf = "00000000000", valor = -50.0)

        `when`(depositoService.verificar(dto)).thenReturn(false)

        val json = objectMapper.writeValueAsString(dto)

        mockMvc.perform(
            post("/deposito")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(status().isUnauthorized)
            .andExpect(content().string("Quantia inválida ou Usuário não encontrado!"))
    }
}
