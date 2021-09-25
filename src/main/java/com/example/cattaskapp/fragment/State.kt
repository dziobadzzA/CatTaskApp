package com.example.cattaskapp.fragment

class State(val name: String) {

    private var v = false

    fun updateState() {
        v = !v
    }

    fun checkState() = v

}