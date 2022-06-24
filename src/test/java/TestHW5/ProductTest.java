package TestHW5;

import HW5.api.ProductService;
import HW5.dto.Product;
import HW5.util.RetrofitUtil;
import com.github.javafaker.Faker;

import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class ProductTest {

    static ProductService productService;
    Product product = null;
    Product bread = new Product(90,"Bread",100,"Food");
    Faker faker = new Faker();
    int id;
    int idMod;

    @BeforeAll
    static void beforeAll() {
        productService = RetrofitUtil.getRetrofit()
                .create(ProductService.class);
    }

    @BeforeEach
    void setUp() {
        product = new Product()
                .withTitle(faker.food().ingredient())
                .withCategoryTitle("Food")
                .withPrice((int) (Math.random() * 10000));
    }

    @Test
    void createProductInFoodCategoryTest() throws IOException {
        Response<Product> response = productService.createProduct(product)
                .execute();
        id =  response.body().getId();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(response.body().getCategoryTitle(), equalTo("Food"));

        Response<ResponseBody> responseDel = productService.deleteProduct(id).execute();
        assertThat(responseDel.isSuccessful(), CoreMatchers.is(true));

    }

    @Test
    void modifyProductInFoodCategoryTest() throws IOException {
        Response<Product> response = productService.createProduct(product)
                .execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        id =  response.body().getId();
        response.body().setPrice(300);

        Response<Product> responseMod = productService.modifyProduct(response.body())
                .execute();
        idMod =  responseMod.body().getId();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        assertThat(idMod, equalTo(id));
        assertThat(responseMod.body().getPrice(), equalTo(300));

        Response<ResponseBody> responseDel = productService.deleteProduct(idMod).execute();
        assertThat(responseDel.isSuccessful(), CoreMatchers.is(true));
    }


    @Test
    void getProductByIdInFoodCategoryTest () throws IOException {
        Response<Product> response = productService.createProduct(product)
                .execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        id =  response.body().getId();

        Response<Product> responseGetId = productService.getProductById(id)
                .execute();
        assertThat(responseGetId.isSuccessful(), CoreMatchers.is(true));
        assertThat(responseGetId.body().getCategoryTitle(), equalTo("Food"));

        Response<ResponseBody> responseDel = productService.deleteProduct(id).execute();
        assertThat(responseDel.isSuccessful(), CoreMatchers.is(true));
    }

    @Test
    void getProductsInFoodCategoryTest () throws IOException {

        Response<Product[]> response = productService.getProducts()
                .execute();
        assert response.body() != null;
    }

    @Test
    void deleteProductByIdInFoodCategoryTest () throws IOException {
        Response<Product> response = productService.createProduct(product)
                .execute();
        assertThat(response.isSuccessful(), CoreMatchers.is(true));
        id =  response.body().getId();

        Response<ResponseBody> responseDel = productService.deleteProduct(id).execute();
        assertThat(responseDel.isSuccessful(), CoreMatchers.is(true));
}}

