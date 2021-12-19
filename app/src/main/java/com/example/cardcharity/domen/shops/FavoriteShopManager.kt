package com.example.cardcharity.domen.shops


/*
- передаём результат каждого клика в менеджер
- обрабатываем, сохраняем в preferences
- НЕ ДОЛЖЕН ВОЗВРАЩАТЬ ПУСТОЕ ЗНАЧЕНИЕ


выдаёт айдишники




в preferences храним "id:count"
сохраняем только первое нажатие. при onStart - можно снова
выдаём первые 5 результатов, с самым большим count
если нет 5 айтемов - берём все





 */
object FavoriteShopManager {
    var block = false


    fun click(shopId: Int) {
        if(block) return
    }





}