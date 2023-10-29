package office.effective.features.user.converters

import office.effective.dto.IntegrationDTO
import office.effective.features.user.repository.IntegrationEntity
import office.effective.model.IntegrationModel
import org.slf4j.LoggerFactory

/**
 * Converters between [IntegrationModel] and [IntegrationEntity]
 * */
class IntegrationModelEntityConverter {
    private val logger = LoggerFactory.getLogger(this::class.java)
    /**
     * Converts [IntegrationModel] to [IntegrationEntity]
     * @param integrationModel [IntegrationModel] input to convert to [IntegrationEntity]
     * @return resulting [IntegrationEntity] object
     *
     * @author Kiselev Danil
     * */
    fun modelToEntity(integrationModel: IntegrationModel): IntegrationEntity {
        logger.trace("Converting integration model to entity")
        return IntegrationEntity {
            id = integrationModel.id
            name = integrationModel.name;
            iconUrl = integrationModel.iconUrl
        }
    }

    /**
     * Converts [IntegrationEntity] to [IntegrationModel]
     * @param integrationEntity [IntegrationEntity] input to convert to [IntegrationModel]
     * @param valueStr [String] users_integrations value
     * @return resulting [IntegrationModel] object
     *
     * @author Kiselev Danil
     * */
    fun entityToModel(integrationEntity: IntegrationEntity, valueStr: String): IntegrationModel {
        logger.trace("Converting integration entity to model")
        return IntegrationModel(
            id = integrationEntity.id!!,
            iconUrl = integrationEntity.iconUrl,
            name = integrationEntity.name,
            valueStr = valueStr
        )
    }
}
