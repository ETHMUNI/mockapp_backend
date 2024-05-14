package org.example.Routes;
/*
import io.restassured.RestAssured;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.Config.ApplicationConfig;
import org.example.Config.HibernateConfig;
import org.example.Populator;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // makes the test methods going in order. Else the delete method is running before anything else an the some of the tests fail
class CarRoutesTest {

    private static EntityManagerFactory emf;

    @BeforeAll
    static void setUpAll() {
        emf = HibernateConfig.getEntityManagerFactory(true);
        RestAssured.baseURI = "http://localhost:7000/api";
        ApplicationConfig applicationConfig = ApplicationConfig.getInstance();
        applicationConfig.initiateServer()
                .startServer(7001)
                .setExceptionHandling()
                .setRoute(Routes.getTestCarRoutes(emf))
                .checkSecurityRoles();
    }

    @BeforeEach
    public void beforeEachTest() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Car").executeUpdate();
        em.createQuery("DELETE FROM Seller").executeUpdate();
        em.getTransaction().commit();
        em.clear();
        em.close();

        new Populator(emf).populate();
    }

    @AfterEach
    public void afterEachTest() {
        EntityManager em = emf.createEntityManager();
        if (em.isOpen()) {
            em.close();
        }
    }

    @AfterAll
    static void tearDownAll() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    @Test
    @Order(1)
    public void getAllCars() {
        RestAssured.given()
                .accept("application/json")
                .when().get("/carshop/cars")
                .then()
                .statusCode(200)
                .contentType("application/json");
    }

    @Test
    @Order(2)
    public void getCarById() {

        int firstCarId = given()
                .accept("application/json")
                .when()
                .get("/carshop/cars")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath().getInt("[1].id");

        RestAssured.given()
                .accept("application/json")
                .when()
                .get("/carshop/cars/" + firstCarId)
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("id", equalTo(firstCarId));
    }

    @Test
    @Order(3)
    public void createCar() {
        String carJson = """
                {
                    "brand": "Toyota",
                    "model": "Corolla",
                    "make": "Sedan",
                    "year": 2022,
                    "firstRegistrationDate": "2022-01-15",
                    "price": 22000.00
                }
                """;
        RestAssured.given()
                .contentType("application/json")
                .accept("application/json")
                .body(carJson)
                .when()
                .post("/carshop/cars")
                .then()
                .statusCode(201)
                .body("model", equalTo("Corolla"));
    }
}
/*
    @Test
    @Order(4)
    public void updateCar() {

        String carJson = """
            {
                "id": 6,
                "brand": "Toyota",
                "model": "Corolla",
                "make": "Sedan",
                "year": 2023,
                "firstRegistrationDate": "2023-01-15",
                "price": 22000.00
            }
            """;
        RestAssured.given()
                .contentType("application/json")
                .accept("application/json")
                .body(carJson)
                .when()
                .put("/carshop/cars/6")
                .then()
                .statusCode(200)
                .body("year", equalTo(2023));
    }

    @Test
    @Order(5)
    public void deleteCar() {

        RestAssured.given()
                .contentType("application/json")
                .when()
                .delete("/carshop/cars/6")
                .then()
                .statusCode(200);
    }
}
*/