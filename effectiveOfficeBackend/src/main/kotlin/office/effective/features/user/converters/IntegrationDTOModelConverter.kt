package office.effective.features.user.converters

import office.effective.features.user.dto.IntegrationDTO
import office.effective.model.IntegrationModel
import java.util.*

class IntegrationDTOModelConverter {
    fun dTOToModel(integrationDTO: IntegrationDTO): IntegrationModel {
        return IntegrationModel(UUID.fromString(integrationDTO.id), integrationDTO.name, integrationDTO.value, "")
    }

    fun modelToDTO(integrationModel: IntegrationModel): IntegrationDTO {
        return IntegrationDTO(integrationModel.id.toString(), integrationModel.name, integrationModel.valueStr)
    }
}