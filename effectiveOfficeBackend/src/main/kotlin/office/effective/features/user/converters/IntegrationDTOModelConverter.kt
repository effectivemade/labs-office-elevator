package office.effective.features.user.converters

import office.effective.common.utils.UuidValidator
import office.effective.dto.IntegrationDTO
import office.effective.model.IntegrationModel

class IntegrationDTOModelConverter(
    private val uuidConverter : UuidValidator
) {
    fun dTOToModel(integrationDTO: IntegrationDTO): IntegrationModel {
        return IntegrationModel(uuidConverter.uuidFromString(integrationDTO.id), integrationDTO.name, integrationDTO.value, "")
    }

    fun modelToDTO(integrationModel: IntegrationModel): IntegrationDTO {
        return IntegrationDTO(integrationModel.id.toString(), integrationModel.name, integrationModel.valueStr)
    }
}