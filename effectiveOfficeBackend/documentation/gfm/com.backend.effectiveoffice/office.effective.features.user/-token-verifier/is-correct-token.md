//[com.backend.effectiveoffice](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/index.md)/[office.effective.features.user](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.features.user/index.md)/[TokenVerifier](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.features.user/-token-verifier/index.md)/[isCorrectToken](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.features.user/-token-verifier/is-correct-token.md)

# isCorrectToken

[jvm]\
open override fun [isCorrectToken](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.features.user/-token-verifier/is-correct-token.md)(tokenString: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

Check Google Id Token from input line. Returns email

#### Author

Kiselev Danil

Kiselev Danil

#### Return

user email

#### Parameters

jvm

| | |
|---|---|
| tokenString | [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) which contains token to verify |

#### Throws

| | |
|---|---|
| [Exception](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-exception/index.html) | (&quot;Token wasn't verified&quot;) if method can't extract email |
