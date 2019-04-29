# hair-salon
An app for a hair salon. The owner should be able to add a list of the stylists, and for each stylist, add clients who see that stylist. The stylists work independently, so each client only belongs to a single stylist.
[Link to App](https://stormy-cliffs-82880.herokuapp.com/)

**Specs**
1. Salon employee can see list of all stylists.
2. Upon selecting a stylist, employee should see their details and list of all clients that belong to the stylist.
3. Employee can add new stylist
4. Employee can add clients to a stylist.
5. Employee can update stylist details.
6. Employee can update client details.
7. Employee can delete stylist.
8. Employee can delete client.

**Technologies and frame works Used**
1. java 11
2. spark core 2.12
3. Gradle 4.10
4. Spark Template Velocity
5. Junit 5
6. Postgres database

**Database**
In psql
> ```
> CREATE DATABASE hair_salon;
> CREATE TABLE stylists(name varchar,phone int,id int);
>CREATE TABLE clients(name varchar,stylisytId int,id int);
