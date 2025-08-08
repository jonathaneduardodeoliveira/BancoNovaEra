package com.banco.nova.era.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.info.License
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition(
    info = Info(
        title = "Banco Nova Era API",
        version = "1.0",
        description = "API desenvolvida por Jonathan e Morone, Gabriel e Rafaela",
        contact = Contact(
            name = "Jonathan e Morone, Gabriel e Rafaela",
            email = "contato@banco-nova-era.com"
        ),
        license = License(name = "Apache 2.0", url = "http://www.apache.org/licenses/LICENSE-2.0.html")
    )
)
@Configuration
class OpenApiConfig
