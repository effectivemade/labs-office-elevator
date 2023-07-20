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
            id = integrationEntity.id!!,
            iconUrl = integrationEntity.iconUrl,
            name = integrationEntity.name,
            valueStr = valueStr
        )
    }
}