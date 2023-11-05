//[com.backend.effectiveoffice](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/index.md)/[office.effective.serviceapi](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.serviceapi/index.md)/[IWorkspaceService](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.serviceapi/-i-workspace-service/index.md)/[findAllFreeByPeriod](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.serviceapi/-i-workspace-service/find-all-free-by-period.md)

# findAllFreeByPeriod

[jvm]\
abstract fun [findAllFreeByPeriod](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.serviceapi/-i-workspace-service/find-all-free-by-period.md)(tag: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), beginTimestamp: [Instant](https://docs.oracle.com/javase/8/docs/api/java/time/Instant.html), endTimestamp: [Instant](https://docs.oracle.com/javase/8/docs/api/java/time/Instant.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Workspace](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.model/-workspace/index.md)&gt;

Returns all workspaces with the given tag which are free during the given period

#### Return

List of [Workspace](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.model/-workspace/index.md) with the given [tag](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.serviceapi/-i-workspace-service/find-all-free-by-period.md)

#### Author

Daniil Zavyalov

#### Parameters

jvm

| | |
|---|---|
| tag | tag name of requested workspaces |
| beginTimestamp | period start time |
| endTimestamp | period end time |
