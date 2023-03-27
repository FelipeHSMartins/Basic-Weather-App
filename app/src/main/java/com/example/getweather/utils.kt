package com.example.getweather

// Check if all chars in the string are letter
fun String.onlyLetters() = all { it.isLetter() || it.isWhitespace() }