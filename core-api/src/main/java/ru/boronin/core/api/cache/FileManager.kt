package ru.boronin.core.api.cache

import java.io.ByteArrayOutputStream
import java.io.InputStream

interface FileManager {

  /**
   * Get String from android file
   *
   * @return - text from file
   */
  fun string(file: String): String

  /**
   * Get ByteArrayOutputStream from InputStream
   *
   * @return - InputStream, ByteArrayOutputStream
   */
  fun byteArrayOutputStream(file: String, block: (InputStream, ByteArrayOutputStream) -> Unit)

  /**
   * Get file from Android file system
   *
   * @return - InputStream
   */
  fun <R> readFile(file: String, block: (InputStream) -> R): R
}