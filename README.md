# Code Structure

This project follows the Hexagonal Architecture pattern, which separates the application's business logic from its infrastructure.

## Overview

The code is organized into the following layers:

* **Monolith**: This layer holds everything together. It is responsible for modules (e.g. launching).
* **Domain**: This layer defines the domain of the application, which encapsulates the business logic of the application.
* **App**: This layer provides the technical capabilities required by the application, including Spring .
* **Adapters**: This layer defines adapters of the application, which implement the ports and provide the technical capabilities required by the application.

## Domain Layer

The domain layer contains the following subdirectories:

* **Entities**: This directory contains the business entities of the application, such as `Course`.
* **Value Objects**: This directory contains the value objects of the application, such as `CourseId`.
* **Events**: This directory contains domain events, such as `CourseEvent`.

## Application Layer

The application layer contains the following subdirectories:

* **Commands**: This directory contains the command handlers of the application, which define the use cases of the application.
* **Queries**: This directory contains the query handlers of the application, which define the data retrieval use cases of the application.
* **Application Services**: This directory contains (Facades) the application services of the application, which orchestrate the use cases of the application.

## Adapters Layer

The adapters layer contains the following subdirectories:

* **Adapters**: This directory contains the adapters of the application, which implement the ports of the application.