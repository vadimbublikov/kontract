package tech.bublikov.kontract

class Attr(
    val metaModel: MetaModel,
    val code: String,
    val name: String,
    val attrType: IAttrType
) {
    var required: Boolean = false
    var referenceKey: ReferenceConstraint? = null
    var collectionKey: CollectionConstraint? = null

    fun copy(
        required: Boolean? = false,
        collectionKey: CollectionConstraint? = null,
        referenceTarget: Attr? = null
    ): Attr {
        val newAttr = Attr(metaModel = this.metaModel, code = this.code, name = this.name, attrType = this.attrType)
        newAttr.required = required ?: this.required
        newAttr.referenceKey = if (referenceTarget != null) ReferenceConstraint(from = newAttr, target = referenceTarget) else null
        newAttr.collectionKey = collectionKey ?: this.collectionKey
        return newAttr
    }

}

/**
 * Represents constraint reference.
 */
data class ReferenceConstraint(
    val target: Attr,
    val from: Attr
)
/**
 * Represents constraint collection.
 */
data class CollectionConstraint(
    val target: Attr,
    val from: Attr
)