package com.example.lab_6_3

data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val date: String
) {
    override fun toString(): String {
        return title // Hiển thị tiêu đề trong RecyclerView
    }
}
