package com.example.lab_6_2

data class Contact(val id: Int, val name: String, val phone: String, val email: String) {
    override fun toString(): String {
        return name // Hiển thị tên trong ListView
    }
}
