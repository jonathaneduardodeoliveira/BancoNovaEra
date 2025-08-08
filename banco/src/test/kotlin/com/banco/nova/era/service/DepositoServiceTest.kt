package com.banco.nova.era.service

import com.banco.nova.era.dto.DepositoDTO
import com.banco.nova.era.dto.EnderecoDTO
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
class DepositoServiceTest {

    @InjectMocks
    lateinit var depositoService: DepositoService

    @Mock
    lateinit var usuarioRepository: UsuarioRepository

    private fun usuarioExemplo(nome: String, cpf: String, conta: Int, valor: Double = 100.0): Usuario {
        return Usuario(
            nome = nome,
            cpf = cpf,
            conta = conta,
            senha = "123456",
            email = "$nome@banco.nova.era",
            telefone = "11999999999",
            endereco = Endereco(
                cep = "00000000",
                logradouro = "Rua Teste",
                bairro = "Centro",
                cidade = "São Paulo",
                uf = "SP",
                numero = "100",
                complemento = "Apto 1"
            ),
            valor = valor
        )
    }

    @Test
    fun `deve realizar deposito para Jonathan`() {
        val usuario = usuarioExemplo("Jonathan", "12345678900", 123456)
        val deposito = DepositoDTO(
            nome = usuario.nome,
            cpf = usuario.cpf,
            senha = usuario.senha,
            email = usuario.email,
            telefone = usuario.telefone,
            conta = usuario.conta.toString(),
            endereco = EnderecoDTO(
                cep = usuario.endereco.cep,
                logradouro = usuario.endereco.logradouro,
                bairro = usuario.endereco.bairro,
                cidade = usuario.endereco.cidade,
                uf = usuario.endereco.uf,
                numero = usuario.endereco.numero,
                complemento = usuario.endereco.complemento
            ),
            valor = 50.0
        )

        `when`(usuarioRepository.findById(usuario.cpf)).thenReturn(Optional.of(usuario))

        val resultado = depositoService.verificar(deposito)

        assertTrue(resultado)
        assertEquals(150.0, usuario.valor)
    }

    @Test
    fun `nao deve realizar deposito para Morone com valor negativo`() {
        val usuario = usuarioExemplo("Morone", "98765432100", 654321)
        val deposito = DepositoDTO(
            nome = usuario.nome,
            cpf = usuario.cpf,
            senha = usuario.senha,
            email = usuario.email,
            telefone = usuario.telefone,
            conta = usuario.conta.toString(),
            endereco = EnderecoDTO(
                cep = usuario.endereco.cep,
                logradouro = usuario.endereco.logradouro,
                bairro = usuario.endereco.bairro,
                cidade = usuario.endereco.cidade,
                uf = usuario.endereco.uf,
                numero = usuario.endereco.numero,
                complemento = usuario.endereco.complemento
            ),
            valor = -10.0
        )

        `when`(usuarioRepository.findById(usuario.cpf)).thenReturn(Optional.of(usuario))

        val resultado = depositoService.verificar(deposito)

        assertFalse(resultado)
        assertEquals(100.0, usuario.valor)
    }

    @Test
    fun `nao deve realizar deposito para Gabriel se usuario nao existe`() {
        val cpf = "00000000000"
        val deposito = DepositoDTO(
            nome = "Gabriel",
            cpf = cpf,
            senha = "000000",
            email = "gabriel@banco.nova.era",
            telefone = "11999999999",
            conta = "999999",
            endereco = EnderecoDTO(
                cep = "00000000",
                logradouro = "Rua Fantasma",
                bairro = "Desconhecido",
                cidade = "Narnia",
                uf = "ND",
                numero = "0",
                complemento = "Nenhum"
            ),
            valor = 20.0
        )

        `when`(usuarioRepository.findById(cpf)).thenReturn(Optional.empty())

        val resultado = depositoService.verificar(deposito)

        assertFalse(resultado)
    }
}
