package office.effective.model

import java.util.*

data class IntegrationModel(var name: String, var valueStr: String) {
    var id: UUID? = null
    var iconUrl: String = ""

    constructor(
        _id: UUID,
        _name: String,
        _valueStr: String,
        _iconUrl: String
    ) : this(_name, _valueStr) {
        this.id = _id
        this.iconUrl = _iconUrl
    }
}
