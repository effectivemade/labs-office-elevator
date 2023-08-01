package office.effective.features.user.converters

import office.effective.features.user.dto.IntegrationDTO
import office.effective.model.IntegrationModel

class IntegrationDTOModelConverter {
    fun dTOToModel(integrationDTO: IntegrationDTO): IntegrationModel {
        return IntegrationModel(null, integrationDTO.name, integrationDTO.value, "")
    }

    fun modelToDTO(integrationModel: IntegrationModel): IntegrationDTO {
        return IntegrationDTO(integrationModel.name, integrationModel.valueStr)
    }
}