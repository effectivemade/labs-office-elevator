//[com.backend.effectiveoffice](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/index.md)/[office.effective.features.user](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.features.user/index.md)/[ApiKeyVerifier](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.features.user/-api-key-verifier/index.md)/[isCorrectToken](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.features.user/-api-key-verifier/is-correct-token.md)

# isCorrectToken

[jvm]\
open override fun [isCorrectToken](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.features.user/-api-key-verifier/is-correct-token.md)(tokenString: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Check api key from input line. String encrypting by SHA-256 and comparing with encrypted keys from database. If it cannot find one, throw InstanceNotFoundException

#### Return

random String

#### Author

Kiselev Danil

#### Parameters

jvm

| | |
|---|---|
| tokenString | [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) which contains token to verify |
