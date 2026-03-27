package tech.bublikov.kontract

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class DslTest {

    @Test
    fun `plain metamodel test`() {

        val country = FinancialDomain.Country

        assertThat(country.code.name).isEqualTo("Числовой код")

    }

    @Test
    fun `metamodel with ref test`() {
        val currency = FinancialDomain.Currency
        val attrRefCountry = currency.attrs.first { it.code == "country" }

        assertThat(attrRefCountry.referenceKey?.target).isEqualTo(FinancialDomain.Country.id)

    }
}



object FinancialDomain: SubDomain {

    object Packages {
        object Dictionary: Package() {
            override val code: String = "dictionary"
            override val name: String = "Dictionary"
        }
    }

    object Country : MetaModel(name = "Страна", pckg = Packages.Dictionary) {
        val code = string(code = "code", name = "Числовой код", length = 3).required()
        val codeAlf2 = string(code = "codeAlf2", name = "Буквенный код Альфа-2", length = 2).required()
        val codeAlf3 = string(code = "codeAlf3", name = "Буквенный код Альфа-3", length = 3).required()
        val name = string(code = "name", name = "Наименование", length = 160).required()
    }

    object Currency : MetaModel(name = "Валюты", pckg = Packages.Dictionary) {
        val code = string(code = "code", name = "Буквенный код", length = 3).required()
        val codeNum = string(code = "codeNum", name = "Числовой код", length = 3).required()
        val name = string(code = "name", name = "Наименование", length = 160).required()
        val country = (long(code = "country", name = "Ссылка на страну") references Country.id).required
    }

}

