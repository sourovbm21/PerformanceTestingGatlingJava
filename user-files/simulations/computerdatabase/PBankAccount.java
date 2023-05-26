package Bank;

import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class PBankAccount extends Simulation {

  private HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://parabank.parasoft.com")
    .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*\\.svg", ".*detectportal\\.firefox\\.com.*"))
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
    .acceptEncodingHeader("gzip, deflate, br")
    .acceptLanguageHeader("en-GB,en-US;q=0.9,en;q=0.8,pt;q=0.7")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36");
  
  private Map<CharSequence, String> headers_0 = Map.ofEntries(
    Map.entry("sec-ch-ua", "Google Chrome\";v=\"113\", \"Chromium\";v=\"113\", \"Not-A.Brand\";v=\"24"),
    Map.entry("sec-ch-ua-mobile", "?0"),
    Map.entry("sec-ch-ua-platform", "macOS"),
    Map.entry("sec-fetch-dest", "document"),
    Map.entry("sec-fetch-mode", "navigate"),
    Map.entry("sec-fetch-site", "same-origin"),
    Map.entry("sec-fetch-user", "?1"),
    Map.entry("upgrade-insecure-requests", "1")
  );
  
  private Map<CharSequence, String> headers_1 = Map.ofEntries(
    Map.entry("origin", "https://parabank.parasoft.com"),
    Map.entry("sec-ch-ua", "Google Chrome\";v=\"113\", \"Chromium\";v=\"113\", \"Not-A.Brand\";v=\"24"),
    Map.entry("sec-ch-ua-mobile", "?0"),
    Map.entry("sec-ch-ua-platform", "macOS"),
    Map.entry("sec-fetch-dest", "document"),
    Map.entry("sec-fetch-mode", "navigate"),
    Map.entry("sec-fetch-site", "same-origin"),
    Map.entry("sec-fetch-user", "?1"),
    Map.entry("upgrade-insecure-requests", "1")
  );
  
  private Map<CharSequence, String> headers_2 = Map.ofEntries(
    Map.entry("accept", "application/json, text/plain, */*"),
    Map.entry("sec-ch-ua", "Google Chrome\";v=\"113\", \"Chromium\";v=\"113\", \"Not-A.Brand\";v=\"24"),
    Map.entry("sec-ch-ua-mobile", "?0"),
    Map.entry("sec-ch-ua-platform", "macOS"),
    Map.entry("sec-fetch-dest", "empty"),
    Map.entry("sec-fetch-mode", "cors"),
    Map.entry("sec-fetch-site", "same-origin")
  );
  
  private Map<CharSequence, String> headers_5 = Map.ofEntries(
    Map.entry("accept", "application/json, text/plain, */*"),
    Map.entry("content-type", "application/json;charset=UTF-8"),
    Map.entry("origin", "https://parabank.parasoft.com"),
    Map.entry("sec-ch-ua", "Google Chrome\";v=\"113\", \"Chromium\";v=\"113\", \"Not-A.Brand\";v=\"24"),
    Map.entry("sec-ch-ua-mobile", "?0"),
    Map.entry("sec-ch-ua-platform", "macOS"),
    Map.entry("sec-fetch-dest", "empty"),
    Map.entry("sec-fetch-mode", "cors"),
    Map.entry("sec-fetch-site", "same-origin")
  );


  private ScenarioBuilder scn = scenario("Bank.PBankAccount")
    .exec(
      http("request_0")
        .get("/parabank/index.htm?ConnType=JDBC")
        .headers(headers_0)
    )
    .pause(14)
    .exec(
      http("Login Request")
        .post("/parabank/login.htm")
        .headers(headers_1)
        .formParam("username", "trauto")
        .formParam("password", "Trauto123")
        .resources(
          http("Get Account Details")
            .get("/parabank/services_proxy/bank/customers/13655/accounts")
            .headers(headers_2)
        )
    )
    .pause(4)
    .exec(
      http("Open Account")
        .get("/parabank/openaccount.htm")
        .headers(headers_0)
        .resources(
          http("request_4")
            .get("/parabank/services_proxy/bank/customers/13655/accounts")
            .headers(headers_2)
        )
    )
    .pause(4)
    .exec(
      http("Create Account Request")
        .post("/parabank/services_proxy/bank/createAccount?customerId=13655&newAccountType=0&fromAccountId=14676")
        .headers(headers_5)
    );

  {
	  setUp(scn.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
  }
}
