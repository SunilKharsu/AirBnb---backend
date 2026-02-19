# airBnbApp - Application Summary

## Overview
`airBnbApp` is a Spring Boot backend for an Airbnb-like booking platform.  
It supports hotel/room management for hotel managers, guest auth/profile flows, hotel search, booking lifecycle, and Stripe-based payment capture.

## Tech Stack
- Java 21
- Spring Boot 3.5.5
- Spring Web, Spring Data JPA, Spring Security
- PostgreSQL (primary), MySQL connector present
- Redis (configured, not heavily used in current code paths)
- JWT authentication (`jjwt`)
- Stripe Java SDK
- OpenAPI/Swagger (`springdoc-openapi`)
- Lombok + ModelMapper

## Core Domain
- `User` with roles (`GUEST`, `HOTEL_MANAGER`)
- `Hotel` owned by a manager
- `Room` under a hotel
- `Inventory` per room per date (booked/reserved/closed/surgeFactor/price)
- `Booking` with status transitions and payment session id
- `Guest` details attached to bookings
- `HotelMinPrice` daily minimum price cache per hotel

## API Surface (context path: `/api/v1`)
- Auth: `/auth/signup`, `/auth/login`, `/auth/refresh`
- Guest/User: `/users/profile`, `/users/myBookings`
- Public browse: `/hotels/search`, `/hotels/{hotelId}/info`
- Booking: `/bookings/init`, `/bookings/{bookingId}/payments`, cancel/status endpoints
- Manager hotel admin: `/admin/hotels/**`
- Manager room admin: `/admin/hotels/{hotelId}/rooms/**`
- Manager inventory admin: `/admin/inventory/rooms/{roomId}`
- Stripe webhook: `/webhook/payment`

## Security Model
- Stateless JWT auth filter.
- Route protection:
  - `/admin/**` -> `ROLE_HOTEL_MANAGER`
  - `/bookings/**`, `/users/**` -> authenticated user
  - all other routes permitted
- Refresh token is set/read via HTTP-only cookie.

## Booking & Payment Flow
1. Guest initializes booking (`RESERVED`) after inventory lock check.
2. Dynamic total price is computed from inventory days and room count.
3. Checkout session is created in Stripe and booking moves to `PAYMENTS_PENDING`.
4. Stripe webhook confirms payment and converts reserved inventory to booked inventory; booking becomes `CONFIRMED`.
5. Cancel flow updates booking/inventory and triggers Stripe refund.

## Dynamic Pricing
Decorator-style pricing pipeline:
- Base room price
- Holiday multiplier (currently hardcoded holiday flag)
- Occupancy multiplier (>80% occupancy)
- Urgency multiplier (within next 7 days)
- Manual surge factor multiplier from inventory

Scheduled service exists to refresh inventory and `HotelMinPrice` data.

## Current Implementation Notes / Risks
- `InventoryServiceImpl.initializeRoomForAYear` loop condition is reversed, so yearly inventory initialization does not run.
- Several controller path variable mismatches and method naming issues in booking endpoints.
- `HotelBookingController` cancel/status routes currently look inconsistent (`booingId` typo, addGuests endpoint calling cancel).
- `InventoryRepository.updateInventory` JPQL update is malformed (missing comma between fields).
- `HotelMinPriceRepository.findHotelWithAvailable` query uses incorrect field reference (`i.hotel.date`).
- Scheduler method pages through hotels but currently does not call `updateHotelPrices(hotel)` in loop.
- Search endpoint uses `GET` with request body, which is often unsupported by clients/proxies.
- `application.properties` contains plaintext secrets/credentials that should be externalized.
- Test coverage is minimal (`AirBnbAppApplicationTests` only).

## Run Notes
- Build tool: Maven wrapper (`./mvnw`)
- Default DB: PostgreSQL (`jdbc:postgresql://localhost:5432/airBnb`)
- Server base path: `/api/v1`
- DDL mode: `create-drop` (data resets on restart)
