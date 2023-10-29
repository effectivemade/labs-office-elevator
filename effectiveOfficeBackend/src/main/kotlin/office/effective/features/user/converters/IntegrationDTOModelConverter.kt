package office.effective.features.user.converters

import office.effective.common.utils.UuidValidator
import office.effective.dto.IntegrationDTO
import office.effective.model.IntegrationModel
import org.slf4j.LoggerFactory

/**
 * Converters between [IntegrationDTO] and [IntegrationModel]
 * */
class IntegrationDTOModelConverter(
    private val uuidConverter : UuidValidator
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    /**
     * Converts [IntegrationDTO] to [IntegrationModel]
     * @param integrationDTO [IntegrationDTO] input to convert to [IntegrationModel]
     * @return resulting [IntegrationModel] object
     *
     * @author Kiselev Danil
     * */
    fun dTOToModel(integrationDTO: IntegrationDTO): IntegrationModel {
        logger.trace("Converting integration dto to model")
        return IntegrationModel(uuidConverter.uuidFromString(integrationDTO.id), integrationDTO.name, integrationDTO.value, "")
    }

    /**
     * Converts [IntegrationModel] to [IntegrationDTO]
     * @param integrationModel [IntegrationModel] input to convert to [IntegrationDTO]
     * @return resulting [IntegrationDTO] object
     *
     * @author Kiselev Danil
     * */
    fun modelToDTO(integrationModel: IntegrationModel): IntegrationDTO {
        logger.trace("Converting integration model to dto")
        return IntegrationDTO(integrationModel.id.toString(), integrationModel.name, integrationModel.valueStr)
    }
}