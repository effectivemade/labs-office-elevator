
rootProject.name = "effective-office"
include("cloud-server", "rpi-server", "gpio")
include("common")
include("composeApp")
include(":tabletApp")
include(":tabletApp:features:selectRoom")
include(":tabletApp:features:roomInfo")
include(":tabletApp:features:network")
include(":tabletApp:features:domain")
include(":tabletApp:features:core")
include(":tabletApp:features:freeNegotiationsScreen")
include("wheel-picker-compose")