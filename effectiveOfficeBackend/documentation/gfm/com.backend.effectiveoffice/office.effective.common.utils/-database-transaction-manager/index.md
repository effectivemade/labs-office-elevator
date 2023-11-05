//[com.backend.effectiveoffice](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/index.md)/[office.effective.common.utils](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.common.utils/index.md)/[DatabaseTransactionManager](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.common.utils/-database-transaction-manager/index.md)

# DatabaseTransactionManager

[jvm]\
class [DatabaseTransactionManager](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.common.utils/-database-transaction-manager/index.md)(database: Database)

Class used for creation database transaction on the facade layer

## Constructors

| | |
|---|---|
| [DatabaseTransactionManager](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.common.utils/-database-transaction-manager/-database-transaction-manager.md) | [jvm]<br>constructor(database: Database) |

## Functions

| Name | Summary |
|---|---|
| [useTransaction](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.common.utils/-database-transaction-manager/use-transaction.md) | [jvm]<br>fun &lt;[T](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.common.utils/-database-transaction-manager/use-transaction.md)&gt; [useTransaction](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.common.utils/-database-transaction-manager/use-transaction.md)(serviceCall: () -&gt; [T](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.common.utils/-database-transaction-manager/use-transaction.md), isolation: TransactionIsolation = TransactionIsolation.READ_COMMITTED): [T](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.common.utils/-database-transaction-manager/use-transaction.md)<br>Executes code in a database transaction. |
