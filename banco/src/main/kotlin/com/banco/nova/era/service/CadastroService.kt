package com.banco.nova.era.service

import com.banco.nova.era.dto.CadastroUsuarioDTO
import com.banco.nova.era.model.Endereco
import com.banco.nova.era.model.Usuario
import com.banco.nova.era.repository.UsuarioRepository
import org.springframework.stereotype.Service

@Service

class CadastroService(private val usuarioRepository: UsuarioRepository) {

    fun cadastrar(dto: CadastroUsuarioDTO): Usuario {
        if (usuarioRepository.existsById(dto.cpf)) {
            throw IllegalArgumentException("Usuário já cadastrado com esse CPF")
        }

        val endereco = Endereco(
            cep = dto.endereco.cep,
            logradouro = dto.endereco.logradouro,
            bairro = dto.endereco.bairro,
            cidade = dto.endereco.cidade,
            uf = dto.endereco.uf,
            numero = dto.endereco.numero,
            complemento = dto.endereco.complemento
        )

        val novoUsuario = Usuario(
            nome = dto.nome,
            cpf = dto.cpf,
            senha = dto.senha,
            email = dto.email,
            telefone = dto.telefone,
            conta = dto.conta,
            endereco = endereco
        )

        return usuarioRepository.save(novoUsuario)
    }
}
