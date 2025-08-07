package com.banco.nova.era.service

import com.banco.nova.era.model.Usuario
import com.banco.nova.era.repository.UsuarioRepository
import org.springframework.stereotype.Service

@Service
class LoginService(private val usuarioRepository: UsuarioRepository) {

    fun autenticar(cpf: String?, conta: Int?, senha: String): Usuario? {
        if (cpf.isNullOrBlank() && conta == null) {
            return null
        }

        val usuario: Usuario? = when {
            !cpf.isNullOrBlank() && conta != null -> {
                val u = usuarioRepository.findById(cpf).orElse(null)
                if (u != null && u.conta == conta) u else null
            }
            !cpf.isNullOrBlank() -> usuarioRepository.findById(cpf).orElse(null)
            conta != null -> usuarioRepository.findAll().firstOrNull { it.conta == conta }
            else -> null
        }

        return if (usuario != null && usuario.senha == senha) usuario else null
    }
}
