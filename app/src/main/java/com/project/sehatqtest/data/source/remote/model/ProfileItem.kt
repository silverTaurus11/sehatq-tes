package com.project.sehatqtest.data.source.remote.model

data class ProfileItem(
    val userName: String?= "",
    val password: String?= "",
    val loginWith: String?= "",
    val tokenThirdParty: String?= ""
)