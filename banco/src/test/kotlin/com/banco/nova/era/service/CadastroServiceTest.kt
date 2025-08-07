package com.banco.nova.era.service

import com.banco.nova.era.dto.CadastroUsuarioDTO
import com.banco.nova.era.dto.EnderecoDTO
import com.banco.nova.era.model.Usuario
import com.banco.nova.era.repository.UsuarioRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class CadastroServiceUnitTest {

    @InjectMocks
    lateinit var cadastroService: CadastroService

    @Mock
    lateinit var usuarioRepository: UsuarioRepository

    @Test
    fun `deve cadastrar usuario quando cpf nao existe`() {
        val dto = CadastroUsuarioDTO(
            nome = "Jonathan",
            cpf = "11122233344",
            senha = "839201",
            email = "jonathan@banco.nova.era",
            telefone = "11988887777",
            conta = 3,
            endereco = EnderecoDTO(
                cep = "12345678",
                logradouro = "Rua C",
                bairro = "Vila",
                cidade = "Campinas",
                uf = "SP",
                numero = "300",
                complemento = "Sala 5"
            )
        )

        `when`(usuarioRepository.existsById(dto.cpf)).thenReturn(false)
        `when`(usuarioRepository.save(any(Usuario::class.java))).thenAnswer { it.arguments[0] }

        val usuarioSalvo = cadastroService.cadastrar(dto)

        assertEquals(dto.nome, usuarioSalvo.nome)
        assertEquals(dto.email, usuarioSalvo.email)
    }

    @Test
    fun `deve lançar excecao se cpf ja estiver cadastrado`() {
        val dto = CadastroUsuarioDTO(
            nome = "Morone",
            cpf = "55566677788",
            senha = "472910",
            email = "morone@banco.nova.era",
            telefone = "11777776666",
            conta = 4,
            endereco = EnderecoDTO(
                cep = "87654321",
                logradouro = "Rua D",
                bairro = "Bairro Novo",
                cidade = "Curitiba",
                uf = "PR",
                numero = "400",
                complemento = "Fundos"
            )
        )

        `when`(usuarioRepository.existsById(dto.cpf)).thenReturn(true)

        val erro = assertThrows<IllegalArgumentException> {
            cadastroService.cadastrar(dto)
        }

        assertEquals("Usuário já cadastrado com esse CPF", erro.message)
    }

    @Test
    fun `deve cadastrar outro usuario com cpf diferente`() {
        val dto = CadastroUsuarioDTO(
            nome = "Gabriel",
            cpf = "99988877766",
            senha = "105384",
            email = "gabriel@banco.nova.era",
            telefone = "11912345678",
            conta = 5,
            endereco = EnderecoDTO(
                cep = "11223344",
                logradouro = "Rua E",
                bairro = "Centro",
                cidade = "Belo Horizonte",
                uf = "MG",
                numero = "500",
                complemento = "Loja"
            )
        )

        `when`(usuarioRepository.existsById(dto.cpf)).thenReturn(false)
        `when`(usuarioRepository.save(any(Usuario::class.java))).thenAnswer { it.arguments[0] }

        val usuarioSalvo = cadastroService.cadastrar(dto)

        assertEquals(dto.nome, usuarioSalvo.nome)
        assertEquals(dto.email, usuarioSalvo.email)
    }

    @Test
    fun `deve cadastrar usuario com nome rafaela`() {
        val dto = CadastroUsuarioDTO(
            nome = "Rafaela",
            cpf = "33322211100",
            senha = "764209",
            email = "rafaela@banco.nova.era",
            telefone = "11987654321",
            conta = 6,
            endereco = EnderecoDTO(
                cep = "44332211",
                logradouro = "Rua F",
                bairro = "Jardim",
                cidade = "Porto Alegre",
                uf = "RS",
                numero = "600",
                complemento = "Cobertura"
            )
        )

        `when`(usuarioRepository.existsById(dto.cpf)).thenReturn(false)
        `when`(usuarioRepository.save(any(Usuario::class.java))).thenAnswer { it.arguments[0] }

        val usuarioSalvo = cadastroService.cadastrar(dto)

        assertEquals(dto.nome, usuarioSalvo.nome)
        assertEquals(dto.email, usuarioSalvo.email)
    }
}
