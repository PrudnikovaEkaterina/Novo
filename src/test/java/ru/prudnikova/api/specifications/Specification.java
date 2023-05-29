package ru.prudnikova.api.specifications;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.aeonbits.owner.ConfigCache;
import ru.prudnikova.config.ProjectConfig;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static ru.prudnikova.api.helpers.CustomAllureListener.withCustomTemplates;


public class Specification {
    static ProjectConfig projectConfig = ConfigCache.getOrCreate(ProjectConfig.class);

    public static RequestSpecification requestSpec = with()
            .filter(withCustomTemplates())
            .baseUri(projectConfig.baseUri())
            .log().all()
            .contentType(ContentType.JSON);

    public static ResponseSpecification responseSpec200 = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200).build();

    public static ResponseSpecification responseSpec204 = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(204).build();

}
