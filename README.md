# IMS Backend

This is the backend for the Inventory Management System (IMS), built with Spring Boot and Java. It provides a comprehensive RESTful API for managing inventory, suppliers, products, clients, purchases, sales (ventes), product returns (retours), and stock movements for business environments.

## Features

- **CRUD Operations** for core entities:
    - Fournisseur (Suppliers)
    - Produit (Products)
    - Client
    - Origine (Origins)
    - Inventaire (Inventory)
    - Achat (Purchases)
    - **Vente (Sales Orders)**
    - **RetourProduit (Product Returns)**
- **Flexible Payment Modes for Ventes**
    - Supports cash (`espèce`), cheque (`cheque`), and other payment methods
    - Conditional fields (advance payment, cheque number) based on payment mode
    - Sale status tracking (`PAYE` / `NON_PAYE`)
- **RetourProduit API**
    - Manage product returns associated with ventes
    - Adjust stock and track return details
- **RESTful API** structure for seamless integration with frontend applications (e.g., Angular)
- **Search & Filter** endpoints for efficient data retrieval (e.g., search fournisseurs by name, produits by name, ventes by client)
- **MySQL Database Integration** with JPA/Hibernate for robust and scalable data persistence
- **Cross-Origin support** for local frontend development (`http://localhost:4200`)

## Main Endpoints

- `/api/fournisseurs` – Manage suppliers (list, search, detail, create, update, delete)
- `/api/produits` – Manage products (list, search, detail, create, update, delete)
- `/api/clients` – Manage clients (list, search, detail, create, update, delete)
- `/api/origines` – Manage product origins (list, search, detail, create, update, delete)
- `/api/inventaire` – Access inventory data
- `/api/achats` – Manage purchases, including searching by purchase order (BL) number
- `/api/ventes` – **Manage sales orders** (list, create, detail, update, delete, filter by client, flexible payment modes)
- `/api/retour-produits` – **Manage product returns** linked to sales orders

## Tech Stack

- **Backend:** Java (Spring Boot)
- **Frontend:** Angular (recommended)
- **Database:** MySQL
- **ORM:** JPA / Hibernate

## Getting Started

1. **Clone the repository**
   ```bash
   git clone https://github.com/spartanmhd/ims-backend.git
   ```
2. **Configure your database**
    - Edit `src/main/resources/application.properties` with your MySQL connection details.

3. **Run the backend**
   ```bash
   mvn spring-boot:run
   ```

4. **API Usage**
    - Use tools like Postman or connect your Angular frontend to the REST endpoints.

## License

This project is provided as-is without a specified license.

---
For more details, review the source code and controller classes.