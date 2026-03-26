package tech.bublikov.kontract

abstract class Group {
    abstract val code: String
    abstract val desc: String
}

open class MetaModel(
    val group: Group,
    name: String = "",
    val modulePrefix: Boolean = false,
    val audit: Boolean = true,
    val transitional: Boolean = false,
) {
    val mcode = javaClass.name
    val mname = name

    val attrs = mutableListOf<Attr>()

    val id: Attr = registerAttr("id", "ID", LongAttrType())

    fun string(code: String, name: String, length: Int): Attr =
        registerAttr(code, name, type = StringAttrType(attrLength=length))

    fun long(code: String, name: String): Attr =
        registerAttr(code, name, LongAttrType())

    fun integer(code: String, name: String): Attr =
        registerAttr(code, name, IntegerAttrType())

    fun decimal(code: String, name: String, precision: Int, scale: Int): Attr =
        registerAttr(code, name, DecimalAttrType(precision, scale))

    fun bool(code: String, name: String): Attr =
        registerAttr(code, name, BooleanAttrType())

    fun date(code: String, name: String): Attr =
        registerAttr(code, name, LocalDateAttrType())

    fun dateTime(code: String, name: String): Attr =
        registerAttr(code, name, LocalDateTimeAttrType())

    fun registerAttr(code: String, name: String, type: IAttrType): Attr {
        return Attr(/*metaModel = this,*/ code = code, name = name, attrType = type).also {
            attrs.addAttr(it)
        }
    }

    private fun MutableList<Attr>.addAttr(attr: Attr) {
        if (this.any { it.code == attr.code }) {
            throw DuplicateAttrException(attr.code, attr.name)
        }
        this.add(attr)
    }

    /** Marks this attribute as required. */
    fun Attr.required(): Attr {
        val newAttr = this.copy(required = true)
        return replaceAttr(this, newAttr)
    }

    fun Attr.replaceAttr(oldAttr: Attr, newAttr: Attr): Attr {
        val index = attrs.indexOf(oldAttr)
        attrs[index] = newAttr
        return newAttr
    }
}

class DuplicateAttrException(attrName: String, metaModelName: String) :
    ExceptionInInitializerError("Duplicate attribute name \"$attrName\" in class \"$metaModelName\"")
