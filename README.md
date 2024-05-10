## Building and Running the Discount Code Management System

To build and run the Discount Code Management System, follow the steps outlined below:

### Step 1: Clone the Repository

Clone the repository to your local machine using the following command:

```bash
git clone https://github.com/KamilWitczak242562/SII-LAT.git
```

### Step 2: Build the Application

Navigate to the root directory of the project and build the application using Maven or Gradle:

```bash
# Using Maven
mvn clean install

# Using Gradle
./gradlew build
```

### Step 3: Run the Application

After successful build, run the application using the following command:

```bash
# Using Maven
mvn spring-boot:run

# Using Gradle
./gradlew bootRun
```

### Step 4: Accessing REST Endpoints

Once the application is up and running, you can access the REST endpoints using tools like Postman or cURL. Below are the available endpoints along with sample queries:

1. **Create a new product**:

    - **URL**: `POST http://localhost:8080/api/v1/product/create`
    - **Sample Request Body**:
      ```json
      {
        "name": "Product Name",
        "description": "Product Description",
        "price": 99.99,
        "currency": "USD"
      }
      ```
    Description is OPTIONAL.

2. **Get all products**:

    - **URL**: `GET http://localhost:8080/api/v1/product/getAll`

3. **Update product data**:

    - **URL**: `PUT http://localhost:8080/api/v1/product/{productId}`
    - **Sample Request Body**:
      ```json
      {
        "name": "Updated Product Name",
        "description": "Updated Product Description",
        "price": 109.99,
        "currency": "USD"
      }
      ```
    Description is OPTIONAL.

4. **Create a new promo code**:

    - **URL**: `POST http://localhost:8080/api/v1/promo_code/create`
    - **Sample Request Body**:
      ```json
      {
        "discountAmount": 25.00,
        "currency": "USD",
        "allowedUsages": 100,
        "secondType":  true
      }
      ```
    Code and ExpirationDate are generated automatically. SecondType value determines if discount is in percentage or not.
5. **Get all promo codes**:

    - **URL**: `GET http://localhost:8080/api/v1/promo_code/getAll`

6. **Get details of a specific promo code**:

    - **URL**: `POST http://localhost:8080/api/v1/promo_code/getOne`
    - **Sample Request Body**:
      DISCOUNT25

7. **Get discount price by providing a product and a promo code**:

    - **URL**: `POST http://localhost:8080/api/v1/discount/calculate/{productId}`
    - **Sample Request Body**:
      DISCOUNT25

8. **Simulate a purchase without promo code**:

    - **URL**: `POST http://localhost:8080/api/v1/purchase/create/{productId}`

9. **Simulate a purchase with promo code**:

    - **URL**: `POST http://localhost:8080/api/v1/purchase/create_with_promo_code/{productId}`
    - **Sample Request Body**:
      DISCOUNT25

10. **Generate a sales report**:

    - **URL**: `GET http://localhost:8080/api/v1/purchase/getSummary`

Make sure to replace `{productId}` with the actual product ID in the URLs above.

### Sample Data for Testing

For testing purposes, the following data is available in the database:

#### Products:

| PRODUCT_ID | CURRENCY | DESCRIPTION                           | NAME        | PRICE  |
|------------|----------|---------------------------------------|-------------|--------|
| 1          | USD      | Powerful laptop with SSD storage      | Laptop      | 1200.0 |
| 2          | USD      |                                       | Smartphone  | 800.0  |
| 3          | PLN      | Wireless noise-canceling headphones  | Headphones  | 250.0  |
| 4          | PLN      |                                       | PC          | 700.0  |

#### Promo Codes:

| PROMO_CODE_ID | ALLOWED_USAGES | CODE                           | CURRENCY | DISCOUNT | EXPIRATION_DATE | IS_SECOND_TYPE |
|---------------|----------------|--------------------------------|----------|----------|-----------------|----------------|
| 1             | 5              | qqIxQ                          | USD      | 20.99    | 2024-12-31      | FALSE          |
| 2             | 10             | YlQgxVGVIEbmqVHNSpsX9KQn       | PLN      | 30.0     | 2024-06-30      | TRUE           |
| 3             | 3              | wFjA4Ok2                       | USD      | 50.5     | 2024-10-31      | FALSE          |

#### Purchases:

| PURCHASE_ID | DATE_OF_PURCHASE | DISCOUNT | REGULAR_PRICE | PRODUCT_ID |
|-------------|------------------|----------|---------------|------------|
| 1           | 2024-05-08       | null     | 1200.0        | 1          |
| 2           | 2024-05-08       | 20.99    | 1200.0        | 1          |
| 3           | 2024-05-08       | 30.0     | 800.0         | 2          |
| 4           | 2024-05-08       | null     | 250.0         | 3          |
| 5           | 2024-05-08       | 50.5     | 700.0         | 4          |

These sample data entries can be used to test the functionalities of the Discount Code Management System effectively.

### Additional Notes

- Ensure that the application is running before sending requests to the endpoints.
- Provide valid JSON request bodies for POST and PUT requests.
- Replace placeholder values with actual data in the sample queries.

By following these instructions, you should be able to build, run, and interact with the Discount Code Management System efficiently.