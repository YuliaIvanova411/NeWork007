package ru.netology.nework007.dto

import ru.netology.nework007.enumer.AttachmentType

data class Attachment(
    val url: String,
    val type: AttachmentType,
)
