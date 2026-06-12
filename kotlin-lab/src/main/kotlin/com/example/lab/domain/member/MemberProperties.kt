package com.example.lab.domain.member

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app.member")
data class MemberProperties(
    val defaultStatus: MemberStatus,
    val maxNameLength: Int
)