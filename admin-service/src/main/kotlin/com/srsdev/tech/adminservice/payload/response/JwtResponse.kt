package com.srsdev.tech.adminservice.payload.response

class JwtResponse(
    var accessToken: String? = null,
    var id: String? = "",
    var username: String = "",
    var email: String? = null,
    val roles: List<String> = listOf(),
    var tokenType: String = "Bearer"
) {
//    constructor(accessToken: String?, id: String?, username: String, email: String?, roles: List<String>) : this()



}