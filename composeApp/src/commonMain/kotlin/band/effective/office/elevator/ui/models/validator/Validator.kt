package band.effective.office.elevator.ui.models.validator

open class Validator : ValidatorMethods {

    override fun checkPhone(phoneNumber: String): Boolean = phoneNumber.length == 10

    override fun checkName(name: String): Boolean = name.isEmpty()

    override fun checkPost(post: String): Boolean = post.isEmpty()

    override fun checkTelegramNick(telegramNick: String): Boolean =
        telegramNick.isNotEmpty() && !telegramNick.contains(char = '@', ignoreCase = true)
}