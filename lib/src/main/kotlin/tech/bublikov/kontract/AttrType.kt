package tech.bublikov.kontract


interface IAttrType {
    fun nullable(): Boolean = true
    fun kotlinType(): String
    fun kotlinImport(): String = ""
    fun jsType(): String
    fun postgresType(): String
}

class BooleanAttrType : IAttrType {
    override fun kotlinType(): String = "Boolean"
    override fun jsType(): String = "boolean"
    override fun postgresType(): String = "boolean"
}

/**
 * Numeric column for storing numbers with the specified [precision] and [scale].
 */
class DecimalAttrType(
    /** Total count of significant digits in the whole number. */
    val precision: Int,
    /** Count of decimal digits in the fractional part. */
    val scale: Int
) : IAttrType {
    override fun kotlinType(): String = "Amount"
    override fun jsType(): String = "number"
    override fun postgresType(): String = "numeric($precision, $scale)"
    override fun kotlinImport(): String = "import ru.gpb.g2.core.common.sys.Amount"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as DecimalAttrType

        if (precision != other.precision) return false
        if (scale != other.scale) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + precision
        result = 31 * result + scale
        return result
    }
}

/**
 * Numeric column for storing 4-byte integers.
 */
class IntegerAttrType : IAttrType {
    override fun kotlinType(): String = "Int"
    override fun jsType(): String = "number"
    override fun postgresType(): String = "integer"
}

/**
 * Numeric column for storing 8-byte integers.
 */
class LongAttrType : IAttrType {
    override fun kotlinType(): String = "Long"
    override fun jsType(): String = "integer"
    override fun postgresType(): String = "bigint"
}

class StringAttrType(
    val attrLength: Int = 255
) : IAttrType {
    override fun kotlinType(): String = "String"
    override fun jsType(): String = "string"
    override fun postgresType(): String = "varchar($attrLength)"
}

class LocalDateAttrType : IAttrType {

    companion object {
        internal val INSTANCE = LocalDateAttrType()
    }

    override fun kotlinType(): String = "LocalDate"
    override fun jsType(): String = "date"
    override fun postgresType(): String = "timestamp without time zone"
    override fun kotlinImport(): String = "import java.time.LocalDate"
}

class LocalDateTimeAttrType : IAttrType {

    companion object {
        internal val INSTANCE = LocalDateTimeAttrType()
    }

    override fun kotlinType(): String = "LocalDateTime"
    override fun jsType(): String = "dateTime"
    override fun postgresType(): String = "timestamp without time zone"
    override fun kotlinImport(): String = "import java.time.LocalDateTime"

}
