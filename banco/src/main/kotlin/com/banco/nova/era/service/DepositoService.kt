package com.banco.nova.era.service

import com.banco.nova.era.dto.DepositoDTO
import com.banco.nova.era.repository.UsuarioRepository
import org.springframework.stereotype.Service

@Service
class DepositoService(private val usuarioRepository: UsuarioRepository) {
    fun verificar(payload: DepositoDTO): Boolean{
        val usuario = usuarioRepository.findById(payload.cpf).orElse(null)

        if((payload.valor > 0) && (usuario != null))  {
            usuario.valor += payload.valor
            return true
        }
        return false
    }

}