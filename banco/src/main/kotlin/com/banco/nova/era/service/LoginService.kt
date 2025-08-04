package com.banco.nova.era.service

import com.banco.nova.era.model.Usuario
import com.banco.nova.era.repository.UsuarioRepository
import org.springframework.stereotype.Service

@Service
class LoginService(private val usuarioRepository: UsuarioRepository) {

    fun autenticar(cpf: String, senha: String): Boolean {
        val usuario = usuarioRepository.findById(cpf).orElse(null)
        return usuario?.senha == senha
    }
}
