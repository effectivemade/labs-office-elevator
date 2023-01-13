# Raspberry band.effective.office.elevator.websocket.client

### Client performs the following tasks:

1. Listening webhooks (elevator calls) from server
2. Validation of request time and hash code
3. If everything is ok, band.effective.office.elevator.websocket.client call elevator via GPIO_01 pin

## Running

Before running this band.effective.office.elevator.websocket.client, start server first.

To test on non-raspberry devices, add `PI4J_PLATFORM=SIMULATED` to the environment variables:
1. Open `Edit configuration` in IntelliJ IDEA ( or Android Studio )
2. Pass `PI4J_PLATFORM=SIMULATED` to environment variable field
3. Click `Apply` and `OK`

Then, execute the following command to run a band.effective.office.elevator.websocket.client:

```bash
./gradlew run
```
