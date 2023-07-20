package office.effective.feature.auth.converters

import office.effective.feature.auth.repository.IntegrationEntity
import office.effective.model.IntegrationModel

class IntegrationModelEntityConverter {
    fun modelToEntity(integrationModel: IntegrationModel): IntegrationEntity {
        return IntegrationEntity {
            id = integrationModel.id
            name = integrationModel.name;
            iconUrl = integrationModel.iconUrl
        }
    }

    fun entityToModel(integrationEntity: IntegrationEntity, valueStr: String): IntegrationModel {
        return IntegrationModel(
            _id = integrationEntity.id!!,
            _iconUrl = integrationEntity.iconUrl,
            _name = integrationEntity.name,
            _valueStr = valueStr
        )
    }
}