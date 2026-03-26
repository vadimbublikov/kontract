package tech.bublikov.kontract

import org.junit.jupiter.api.Test

class DslTest {

    @Test
    fun `plain metamodel test`() {

        val country = Country

    }



}

object Groups {
    object Dictionary: Group() {
        override val code: String = "Dictionary"
        override val desc: String = "Справочники"
    }
}

object Country : MetaModel(name = "Страна", group = Groups.Dictionary) {
    val code = string(code = "code", name = "Числовой код", length = 3).required()
    val codeAlf2 = string(code = "codeAlf2", name = "Буквенный код Альфа-2", length = 2).required()
    val codeAlf3 = string(code = "codeAlf3", name = "Буквенный код Альфа-3", length = 3).required()
    val name = string(code = "name", name = "Наименование", length = 160).required()
}