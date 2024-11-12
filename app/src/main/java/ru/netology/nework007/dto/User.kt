package ru.netology.nework007.dto

data class User(
    val id: Int,
    val login: String?,
    val name: String?,
    val avatar: String? = null,
    val isSelected: Boolean = false,
)
