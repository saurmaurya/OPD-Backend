package com.srsdev.tech.adminservice.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "user")
class User {
    @Id
    var id: String? = null
    var username: String = ""
    var email: String = ""

    @JsonIgnore
    var password: String = ""
    var isActive: Boolean = false

    @DBRef
    var roles: Set<Role> = mutableSetOf()

    constructor()
    constructor(username: String, email: String, password: String) {
        this.username = username
        this.email = email
        this.password = password
    }
}