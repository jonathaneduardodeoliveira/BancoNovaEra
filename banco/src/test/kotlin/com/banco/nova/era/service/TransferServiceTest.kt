package com.banco.nova.era.service

import com.banco.nova.era.dto.TransferDTO
import com.banco.nova.era.dto.TransferLogStatus
import com.banco.nova.era.model.Usuario
import com.banco.nova.era.repository.UsuarioRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class TransferServiceTest {

    private lateinit var usuarioRepository: UsuarioRepository
    private lateinit var transferLogService: TransferLogService
    private lateinit var transferService: TransferService

    @BeforeEach
    fun setup() {
        usuarioRepository = mock(UsuarioRepository::class.java)
        transferLogService = mock(TransferLogService::class.java)
        transferService = TransferService(usuarioRepository, transferLogService)
    }

    @Test
    fun `transferencia entre jonathan e gabriel com sucesso`() {
        val jonathan = Usuario(cpf = "111", valor = 200.0)
        val gabriel = Usuario(cpf = "222", valor = 100.0)
        val payload = TransferDTO(origem = "111", destino = "222", valor = 50.0)

        `when`(usuarioRepository.findById("111")).thenReturn(Optional.of(jonathan))
        `when`(usuarioRepository.findById("222")).thenReturn(Optional.of(gabriel))

        val resultado = transferService.verificar(payload)

        assertTrue(resultado)
        assertEquals(150.0, jonathan.valor)
        assertEquals(150.0, gabriel.valor)
        verify(transferLogService).logTransfer(payload, TransferLogStatus.SUCCESS)
    }

    @Test
    fun `transferencia com valor zero entre jonathan e morone deve falhar`() {
        val jonathan = Usuario(cpf = "111", valor = 200.0)
        val morone = Usuario(cpf = "333", valor = 80.0)
        val payload = TransferDTO(origem = "111", destino = "333", valor = 0.0)

        `when`(usuarioRepository.findById("111")).thenReturn(Optional.of(jonathan))
        `when`(usuarioRepository.findById("333")).thenReturn(Optional.of(morone))

        val resultado = transferService.verificar(payload)

        assertFalse(resultado)
        verify(transferLogService).logTransfer(payload, TransferLogStatus.FAILED)
    }

    @Test
    fun `transferencia falha quando jonathan nao existe`() {
        val rafaela = Usuario(cpf = "444", valor = 120.0)
        val payload = TransferDTO(origem = "555", destino = "444", valor = 40.0)

        `when`(usuarioRepository.findById("555")).thenReturn(Optional.empty())
        `when`(usuarioRepository.findById("444")).thenReturn(Optional.of(rafaela))

        val resultado = transferService.verificar(payload)

        assertFalse(resultado)
        verify(transferLogService).logTransfer(payload, TransferLogStatus.FAILED)
    }

    @Test
    fun `transferencia falha quando rafaela nao existe`() {
        val gabriel = Usuario(cpf = "222", valor = 90.0)
        val payload = TransferDTO(origem = "222", destino = "666", valor = 20.0)

        `when`(usuarioRepository.findById("222")).thenReturn(Optional.of(gabriel))
        `when`(usuarioRepository.findById("666")).thenReturn(Optional.empty())

        val resultado = transferService.verificar(payload)

        assertFalse(resultado)
        verify(transferLogService).logTransfer(payload, TransferLogStatus.FAILED)
    }
}
