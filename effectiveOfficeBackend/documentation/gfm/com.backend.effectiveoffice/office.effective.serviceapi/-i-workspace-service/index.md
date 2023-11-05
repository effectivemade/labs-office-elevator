//[com.backend.effectiveoffice](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/index.md)/[office.effective.serviceapi](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.serviceapi/index.md)/[IWorkspaceService](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.serviceapi/-i-workspace-service/index.md)

# IWorkspaceService

interface [IWorkspaceService](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.serviceapi/-i-workspace-service/index.md)

Interface that provides methods for manipulating [Workspace](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.model/-workspace/index.md) objects.

#### Inheritors

| |
|---|
| [WorkspaceService](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.features.workspace.service/-workspace-service/index.md) |

## Functions

| Name | Summary |
|---|---|
| [findAllByTag](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.serviceapi/-i-workspace-service/find-all-by-tag.md) | [jvm]<br>abstract fun [findAllByTag](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.serviceapi/-i-workspace-service/find-all-by-tag.md)(tag: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Workspace](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.model/-workspace/index.md)&gt;<br>Returns all workspaces with the given tag |
| [findAllFreeByPeriod](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.serviceapi/-i-workspace-service/find-all-free-by-period.md) | [jvm]<br>abstract fun [findAllFreeByPeriod](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.serviceapi/-i-workspace-service/find-all-free-by-period.md)(tag: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html), beginTimestamp: [Instant](https://docs.oracle.com/javase/8/docs/api/java/time/Instant.html), endTimestamp: [Instant](https://docs.oracle.com/javase/8/docs/api/java/time/Instant.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[Workspace](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.model/-workspace/index.md)&gt;<br>Returns all workspaces with the given tag which are free during the given period |
| [findAllZones](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.serviceapi/-i-workspace-service/find-all-zones.md) | [jvm]<br>abstract fun [findAllZones](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.serviceapi/-i-workspace-service/find-all-zones.md)(): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-list/index.html)&lt;[WorkspaceZone](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.model/-workspace-zone/index.md)&gt;<br>Returns all workspace zones |
| [findById](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.serviceapi/-i-workspace-service/find-by-id.md) | [jvm]<br>abstract fun [findById](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.serviceapi/-i-workspace-service/find-by-id.md)(id: [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/UUID.html)): [Workspace](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.model/-workspace/index.md)?<br>Retrieves a Workspace model by its id |
