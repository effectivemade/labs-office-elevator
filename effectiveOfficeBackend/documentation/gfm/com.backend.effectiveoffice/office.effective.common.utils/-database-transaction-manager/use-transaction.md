//[com.backend.effectiveoffice](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/index.md)/[office.effective.common.utils](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.common.utils/index.md)/[DatabaseTransactionManager](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.common.utils/-database-transaction-manager/index.md)/[useTransaction](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.common.utils/-database-transaction-manager/use-transaction.md)

# useTransaction

[jvm]\
fun &lt;[T](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.common.utils/-database-transaction-manager/use-transaction.md)&gt; [useTransaction](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.common.utils/-database-transaction-manager/use-transaction.md)(serviceCall: () -&gt; [T](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.common.utils/-database-transaction-manager/use-transaction.md), isolation: TransactionIsolation = TransactionIsolation.READ_COMMITTED): [T](IdeaProjects/labs-office-elevator/effectiveOfficeBackend/documentation/gfm/com.backend.effectiveoffice/office.effective.common.utils/-database-transaction-manager/use-transaction.md)

Executes code in a database transaction.

Rollbacks the transaction if an exception was thrown.

#### Parameters

jvm

| | |
|---|---|
| serviceCall | lambda function to be executed |
| isolation | transaction isolation |
