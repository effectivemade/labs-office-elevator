package band.effective.office.elevator.data

import band.effective.office.elevator.domain.models.EmployeeInfo
import band.effective.office.elevator.domain.models.User

object MockUsers {
    val mutableListUsers = mutableListOf(
        User(
            id = "1",
            imageUrl = "pry.jpg",
            userName = "Ivanov Ivan",
            post = "Android-developer",
            telegram = "@fldf",
            phoneNumber = "+79502113241",
            email = "fgfg@effectiveband"
        ) ,
        User(
            id = "2",
            imageUrl = "oii.jpg",
            userName = "Petrov Ivan",
            post = "Android-developer",
            telegram = "@kjhf",
            phoneNumber = "+79502113242",
            email = "ghfgh@effectiveband"
        ),
        User(
            id = "3",
            imageUrl = "ghh.jpg",
            userName = "Ivanov Petr",
            post = "Android-developer",
            telegram = "@fgds",
            phoneNumber = "+79502113243",
            email = "mnmgu@effectiveband"
        )
    )


    val mutableListEmployee = mutableListOf(
            EmployeeInfo(
                "1",
                "Ivanov Ivan",
                "Android-developer",
                "In office",
                "https://wampi.ru/image/R9C6OC7",
                "+79137894523",
                "employee@effective.com",
                "@Ivanov_Ivan"
            ),
            EmployeeInfo(
                "2",
                "Smirnov Andrey",
                "UI/UX Designer",
                "Will be today",
                "https://www.kasandbox.org/programming-images/avatars/leaf-grey.png",
                "+79263452312",
                "employee2@effective.com",
                "@Smirnov_Andrey"
            ),
            EmployeeInfo(
                "3",
                "Vasiliev Vasiliy",
                "HR",
                "No bookings",
                "https://www.kasandbox.org/programming-images/avatars/leaf-blue.png",
                "+79621234434",
                "employee3@effective.com",
                "@Vasiliev_Vasiliy"
        )
    )
}