//[com.backend.effectiveoffice](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/index.md)/[office.effective.features.user](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.features.user/index.md)/[ApiKeyVerifier](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.features.user/-api-key-verifier/index.md)

# ApiKeyVerifier

[jvm]\
class [ApiKeyVerifier](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.features.user/-api-key-verifier/index.md) : [ITokenVerifier](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.features.user/-i-token-verifier/index.md)

[ITokenVerifier](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.features.user/-i-token-verifier/index.md) implementation. Needs to check api keys

## Constructors

| | |
|---|---|
| [ApiKeyVerifier](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.features.user/-api-key-verifier/-api-key-verifier.md) | [jvm]<br>constructor() |

## Functions

| Name | Summary |
|---|---|
| [isCorrectToken](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.features.user/-api-key-verifier/is-correct-token.md) | [jvm]<br>open override fun [isCorrectToken](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.features.user/-api-key-verifier/is-correct-token.md)(tokenString: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Check api key from input line. String encrypting by SHA-256 and comparing with encrypted keys from database. If it cannot find one, throw InstanceNotFoundException |
