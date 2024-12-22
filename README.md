# APT-microservices-project
## Project overzicht
Dit project in een service waarmee, vakantiereizen kunnen worden geboekt.

## Microservices
- **BookingService**: Beheert boekingsoperaties.
- **TravelService**: Beheert reizen.
- **TravelerService**: Beheert reizigersprofielen.
## API Gateway
## Schema
![image](https://github.com/user-attachments/assets/d3fd00d4-2c4d-4223-9256-bad3dbfc43d8)
## Endpoints
- **BookingService** (Poort 8084)
  - `GET /api/bookings`
  - `POST /api/booking`
  - `DELETE /api/booking`
- **TravelService** (Poort 8085)
  - `GET /api/trips`
  - `PUT /api/trip/{id}`
- **TravelerService** (Poort 8086)
  - `GET /api/travelers`
  - `POST /api/traveler`
