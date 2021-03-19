package com.vitalykalenik.aviatest.domain.models

/**
 * Абстрактный класс конвертера типов
 *
 * @author Vitaly Kalenik
 */
abstract class OneWayConverter<From, To> {

    abstract fun convert(from: From): To

    fun convertList(list: List<From>): List<To> = list.map { convert(it) }
}