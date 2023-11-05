//[com.backend.effectiveoffice](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/index.md)/[office.effective.features.user](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.features.user/index.md)/[TokenVerifier](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.features.user/-token-verifier/index.md)

# TokenVerifier

[jvm]\
class [TokenVerifier](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.features.user/-token-verifier/index.md) : [ITokenVerifier](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.features.user/-i-token-verifier/index.md)

Implementation of [ITokenVerifier](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.features.user/-i-token-verifier/index.md). Checks GoogleIdTokens

## Constructors

| | |
|---|---|
| [TokenVerifier](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.features.user/-token-verifier/-token-verifier.md) | [jvm]<br>constructor() |

## Functions

| Name | Summary |
|---|---|
| [isCorrectToken](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.features.user/-token-verifier/is-correct-token.md) | [jvm]<br>open override fun [isCorrectToken](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.features.user/-token-verifier/is-correct-token.md)(tokenString: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Check Google Id Token from input line. Returns email |
