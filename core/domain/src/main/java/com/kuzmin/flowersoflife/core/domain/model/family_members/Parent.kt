package com.kuzmin.flowersoflife.core.domain.model.family_members

data class Parent(
    val parentId: String,

    val children: List<Child>
)
