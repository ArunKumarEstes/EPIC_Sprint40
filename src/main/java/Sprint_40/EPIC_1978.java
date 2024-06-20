package Sprint_40;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.Status;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class EPIC_1978 extends UtilClass {

	public static String sendkeys = "";
	public static String BOL_Order = "";

	public EPIC_1978(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//iframe[@name='PegaGadget0Ifr']")
	public static WebElement frameName;

	@FindBy(xpath = "//iframe[@name='PegaGadget1Ifr']")
	public static WebElement frameName2;

	@FindBy(id = "loginText2")
	public static WebElement ssoLogin;

	@FindBy(className = "table-row")
	public static WebElement code;

	@FindBy(id = "idTxtBx_SAOTCC_OTC")
	public static WebElement send;

	@FindBy(id = "idSubmit_SAOTCC_Continue")
	public static WebElement click;

	@FindBy(xpath = "//div[contains(@class,'launch-portals')]/descendant::a")
	public static WebElement LaunchPortal;

	@FindBy(xpath = "//span[contains(text(),'WareHouse UserPortal')]")
	public static WebElement warehouse;

	@FindBy(xpath = "//li[@title='Orders PAR']")
	public static WebElement OrdersPAR;

	@FindBy(xpath = "//h3[contains(text(),'OS&D')]")
	public static WebElement OSD;

	@FindBy(xpath = "//h3[contains(text(),'Outbound Loads')]")
	public static WebElement Outbound_loads;
	

	public static void frameSwitch() {
		driver.switchTo().frame(frameName);

	}

	public void PAR_Order_Creation() throws Exception {

		String prettyString = null;
		File file = new File(System.getProperty("user.dir") + "\\PAR.json");
		ObjectMapper objectMapper = new ObjectMapper();
		// Read JSON from file into a JsonNode
		JsonNode rootNode = objectMapper.readTree(file);
		// Ensure that the root node is an ObjectNode
		if (rootNode instanceof ObjectNode) {
			ObjectNode objectNode = (ObjectNode) rootNode;
			// Retrieve the "OrderRefs" object and add "InvoiceNumber" with a random value
			ObjectNode orderRefs = (ObjectNode) objectNode.get("OrderRefs");
			double ceil = Math.ceil(Math.random() * 100000000);
			BOL_Order = Double.toString(ceil);
			orderRefs.put("BOL", BOL_Order);
			orderRefs.put("TrackingNumber", Math.ceil(Math.random() * 100000000));
			orderRefs.put("InvoiceNumber", Math.ceil(Math.random() * 100000000));
			// Convert the modified JsonNode back to a pretty-printed string
			prettyString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectNode);

			RestAssured.baseURI = "https://epicuatlb.estes-express.com";
			Response response = RestAssured.given().auth().basic("EpicSevicesTest1", "Rules@1234")
					.contentType("application/json").body(prettyString)
					.post("/prweb/api/OrderServicePackage/V1/CreateOrUpdateOrder");
			String responseBody = response.getBody().asString();
			String[] split = responseBody.split("Reference is ");

			sendkeys = split[1];
			System.out.println(sendkeys);
			System.out.println("<------Result of PAR Json------>");
			System.out.println("Response Body: " + responseBody);
			int statusCode = response.getStatusCode();
			System.out.println("Status Code: " + statusCode);
			extentTest.log(Status.PASS, "Order Created");

		}

	}

	public static void frameswitch2() {
		driver.switchTo().frame(frameName2);
	}

	public void PEGALogin() throws InterruptedException {
		ssoLogin.click();
		waits(code);
		code.click();

		// Scanner class to handle OTP
		String scanner = scanner();
		send.sendKeys(scanner);
		waits(click);
		click.click();
	}

	public void LaunchWarehousePortal() throws InterruptedException {
		waits(LaunchPortal);
		LaunchPortal.click();
		waits(warehouse);
		warehouse.click();
		Await();
		Windows();

	}

	public void OrdersPAR() throws Exception {
		Await();
		OrdersPAR.click();
		extentTest.log(Status.PASS, "Entering into Orders PAR");
		Await();
		frameSwitch();
		waits(OSD);
		OSD.click();
		Thread.sleep(9000);
		Outbound_loads.click();
		Await();

	}
}
