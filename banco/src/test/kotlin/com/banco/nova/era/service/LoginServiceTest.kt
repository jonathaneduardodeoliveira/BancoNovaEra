package com.banco.nova.era.service

import com.banco.nova.era.model.Endereco
import com.banco.nova.era.model.Usuario
import com.banco.nova.era.repository.UsuarioRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class LoginServiceTest {

    @InjectMocks
    lateinit var loginService: LoginService

    @Mock
    lateinit var usuarioRepository: UsuarioRepository

    private fun usuarioExemplo(nome: String, cpf: String, conta: Int, senha: String): Usuario {
        return Usuario(
            nome = nome,
            cpf = cpf,
            senha = senha,
            email = "$nome@banco.nova.era",
            telefone = "11999999999",
            conta = conta,
            endereco = Endereco(
                cep = "00000000",
                logradouro = "Rua Teste",
                bairro = "Centro",
                cidade = "São Paulo",
                uf = "SP",
                numero = "100",
                complemento = "Apto 1"
            )
        )
    }

    @Test
    fun `deve autenticar com cpf e conta corretos`() {
        val usuario = usuarioExemplo("Jonathan", "12345678900", 123456, "839201")
        `when`(usuarioRepository.findById("12345678900")).thenReturn(Optional.of(usuario))

        val resultado = loginService.autenticar("12345678900", 123456, "839201")

        assertNotNull(resultado)
        assertEquals("Jonathan", resultado?.nome)
    }

    @Test
    fun `deve autenticar apenas com cpf correto`() {
        val usuario = usuarioExemplo("Morone", "98765432100", 654321, "472910")
        `when`(usuarioRepository.findById("98765432100")).thenReturn(Optional.of(usuario))

        val resultado = loginService.autenticar("98765432100", null, "472910")

        assertNotNull(resultado)
        assertEquals("Morone", resultado?.nome)
    }

    @Test
    fun `deve autenticar apenas com conta correta`() {
        val usuario = usuarioExemplo("Gabriel", "11122233344", 112233, "105384")
        `when`(usuarioRepository.findAll()).thenReturn(listOf(usuario))

        val resultado = loginService.autenticar(null, 112233, "105384")

        assertNotNull(resultado)
        assertEquals("Gabriel", resultado?.nome)
    }

    @Test
    fun `nao deve autenticar com senha incorreta`() {
        val usuario = usuarioExemplo("Rafaela", "33322211100", 998877, "764209")
        `when`(usuarioRepository.findById("33322211100")).thenReturn(Optional.of(usuario))

        val resultado = loginService.autenticar("33322211100", 998877, "000000")

        assertNull(resultado)
    }

    @Test
    fun `nao deve autenticar sem cpf e sem conta`() {
        val resultado = loginService.autenticar(null, null, "123456")

        assertNull(resultado)
    }
}
