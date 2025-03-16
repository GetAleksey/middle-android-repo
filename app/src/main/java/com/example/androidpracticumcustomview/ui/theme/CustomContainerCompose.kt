package com.example.androidpracticumcustomview.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect


/*
Задание:
Реализуйте необходимые компоненты;
Создайте проверку что дочерних элементов не более 2-х;
Предусмотрите обработку ошибок рендера дочерних элементов.
Задание по желанию:
Предусмотрите параметризацию длительности анимации.
 */
@Composable
fun CustomContainerCompose(
    firstChild: @Composable (() -> Unit)?,
    secondChild: @Composable (() -> Unit)?
) {
    // Блок создания и инициализации переменных
    // ..

    // Блок активации анимации при первом запуске
    LaunchedEffect(Unit) {
        // TODO
        // ...
    }

    // Основной контейнер
    Box() {
        // TODO
        // ...
    }
}