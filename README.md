# Buy_EZ
### Rest Api indvidual project for an ecommerce application which can be accessed by both customer and admin.
 
 
 #### Relationships among entities:
 <img src="https://user-images.githubusercontent.com/105979795/211373871-5dc9fc57-f9f6-4ec5-bd31-827cf55a6bb6.png"></img>
 

## Api root endpoint

````
http://localhost:8765
````
### Installation & Run
- Before running the API server, you have to update the database configuration inside the application.properties file
- Update the port number, username and password as per your local database configuration
````
    server.port=8765

    spring.datasource.url=jdbc:mysql://localhost:3306/cabdb;
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver;
    spring.datasource.username=root
    spring.datasource.password=root
    
````

## Auth Controller:-
* userRegister
````
POST request

http://localhost:8765/register

     body :{
     "username":"",
     "firstName":"",
     "lastName":"",
     "password":"",
     "address":{
      "city" : "",
      "state": "", 
      "pincode": "",
      "details": ""
        },
     "mobileNumber":"",
     "email":"",
     "country":""
   }

   ````
   * User login
   ````
 POST request 
 
  http://localhost:8765/login

 body : {
  "id":"",
  "username":"",
  "password":""
 }

  ````
  * userLogout
  ````
  DELETE request
  
  http://localhost:8765/logout
  ````
## User Controller:-

##### To access most of user controller api's you have to provide the jwt token you recieve as a response while logging in inside the authorization header with the http request.
````
headers:{
 'Authorization':'Bearer {JWTtoken}' 
}
````
* searchProductByName- 
 ````
 GET request
 
 http://localhost:8765/search?name={name}
 ````
* searchProductsByCategoryName- 
````
GET request

http://localhost:8765/search/category?categoryName={categoryName}
````
* addRatings- 
````
PUT request

http://localhost:8765/add-rating?productId={productId}&rating={rating between 1-5}
````
* getProductDetails- 
````
GET request

http://localhost:8765/searchById?productId={productId}
````
* addProductToCart- 
````
POST request

http://localhost:8765/add-cart?productId={productId}&quantity={quantity}
````
* deleteProductFromCart- 
````
DELETE request

http://localhost:8765/delete-cart?productId={productId}
````
* getCartDetails- 
````
GET request

http://localhost:8765/cart
````
* placeOrder- 
````
POST request

http://localhost:8765/cart/order?paymentType={paymentType}
{

  body:{
      address : {
      "city":"",
      "state":"",
      "pincode":"",
      "details":""  
     } 
    }

}
````
* getProductsFromOrder- 
````
GET request

http://localhost:8765/order/products?orderId={orderId}
````
* getAllCategories- 
````
GET request

http://localhost:8765/categories
````
* getAllSubCategories- 
````
GET request

http://localhost:8765/subCategories
````
* getUserDetails- 
````
GET request

http://localhost:8765/details
````
* getAllUserOrders- 
````
GET request

http://localhost:8765/orders
````
* getAllPayments- 
````
GET request

http://localhost:8765/payments
````
* updateQuantity- 
##### Updates the quantity of the product presented in the cart of the user.
```
PUT request

http://localhost:8765/update-quantity?productId=&quantity
````
* searchProductsByNameSortHighToLow- 
````
GET request

http://localhost:8765/search/HTL-price?name={name to search products with}
````
* searchProductsByNameSortLowToHigh- 
````
GET request

http://localhost:8765/search/LTH-price?name={name to search products with}
````
* searchProductByNamesSortHighToLowRatings- 
````
GET request

http://localhost:8765/search/HTL-ratings?name={name to search products with}
````
* searchProductByNamesSortLowToHighRatings- 
````
GET request

http://localhost:8765/search/LTH-ratings?name={name to search products with}
````
* updateAddress
````
PUT request

http://localhost:8765/update-address

 body: {
     address : {
      "city":"",
      "state":"",
      "pincode":"",
      "details":""  
     } 
}
````
* updateUsername
````
PUT request

http://localhost:8765/update-username?username={newUsername}

````
* updatePassword
````
PUT request

http://localhost:8765/update-password?password={newPassword}
````

## Admin controller:-
##### All the api's for this controller are secured and can only be accessed by an admin, so if you are registred to the application as an admin than you just have to login and pass the jwt toke in the http request header as authorization.
````
headers:{
    'Authorization':'Bearer {JWTToken}'  
}
````
* addAdmin- 
````
POST request

http://localhost:8765/add

  'body': {
       "username":"",
     "firstName":"",
     "lastName":"",
     "password":"",
     "address":{
      "city" : "",
      "state": "", 
      "pincode": "",
      "details": ""
        },
     "mobileNumber":"",
     "email":"",
     "country":""
     }

````
* addCategory- 
````
POST request

http://localhost:8765/add-category

   'body': {
     "categoryName":""
     }

  ````
* addProduct- 
````
POST request

http://localhost:8765/add-product/{categoryName}

   'body': {
   "productName":"",
   "market_price":"",
   "sale_price":"",
   "color":"",
   "dimension":""
   "specification":"",
   "manufacturer":"",
   "quantity":"",
   "imageUrl":[]
     }

  ````
* addPayment- 
````
POST request

http://localhost:8765/add-payment

   'body': {
     "type":"",
     "allowed":"(yes/no)"
     }

  ````
* addShipper- 
````
POST request

http://localhost:8765/add-shipper

   'body': {
     "companyName":"",
     "mobileNumber":""
     }

  ````
* addSupplier- 
````
POST request

http://localhost:8765/add-supplier

   'body': {
     "companyName":"",
     "city":"",
     "state":"",
     "postalCode":"",
     "country":"",
     "mobileNumber":"",
     "email":""
     }

  ````
* addSubCategory- 
````
POST request

http://localhost:8765/add-subCategory

   'body': {
     "name":""
     }
  
  ````
* getCustomerByOrder- 
````
GET request

http://localhost:8765/order/customer?orderId={orderId}
````
























