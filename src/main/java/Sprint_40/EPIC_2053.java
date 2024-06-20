package Sprint_40;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.codehaus.jettison.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.aventstack.extentreports.Status;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class EPIC_2053 extends UtilClass {
	public static String sendkeys = "";
	public static String BOL_Order = "";
	public static String DateTime = "";
	public static String SearchBox_Text = "OrderServicePackage";
	public static String Arrived_POD = "";
	public static String Depart_POD = "";

	public EPIC_2053(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public void PAR_Order_Creation() throws Exception {

		String dirpath = System.getProperty("user.dir");
		File file = new File(dirpath + "//PAR.json");
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(file);
		String actual = node.toPrettyString();
		JSONObject jsonObject = new JSONObject(actual);
		JSONObject orderRefs = jsonObject.getJSONObject("OrderRefs");

//		double ceil = Math.ceil(Math.random() * 100000000);
//		BOL_Order = Double.toString(ceil);
//		orderRefs.put("BOL", BOL_Order);
		orderRefs.put("BOL", Math.ceil(Math.random() * 100000000));
		orderRefs.put("TrackingNumber", Math.ceil(Math.random() * 100000000));
		orderRefs.put("InvoiceNumber", Math.ceil(Math.random() * 100000000));

		RestAssured.baseURI = "https://epicuatlb.estes-express.com";
		Response response = RestAssured.given().auth().basic("EpicSevicesTest1", "Rules@1234")
				.contentType("application/json").body(jsonObject.toString())
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

	@FindBy(xpath = "//iframe[@name='PegaGadget0Ifr']")
	public static WebElement frameName;

	@FindBy(xpath = "//iframe[@name='PegaGadget1Ifr']")
	public static WebElement frameName1;

	@FindBy(xpath = "//iframe[@name='PegaGadget2Ifr']")
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

	@FindBy(xpath = "//h3[contains(text(),'Inbound Trailer')]")
	public static WebElement InboundTrailer;

	@FindBy(xpath = "//th[@aria-label='Order ID']//a[@class='filter highlight-ele']")
	public static WebElement OrderFilter;

	@FindBy(xpath = "//div[@class='content-inner ']/div/span/input[@type='text']")
	public static WebElement OrderSearch_Filter;

	@FindBy(xpath = "(//input[@type='checkbox' and contains(@name,'Inbound')])[21]")
	public static WebElement FilterCheckBox;

	@FindBy(xpath = "(//button[@class='pzhc pzbutton'])[1]")
	public static WebElement OrderFilterApply;

	@FindBy(xpath = "//a[contains(text(), 'PAR')]")
	public static WebElement ClickPARCaseID;

	@FindBy(xpath = "//*[contains(@name, '$PpyWorkPage$pStatusEvent')]")
	public static WebElement Inbound_trailer_outboundLoads_Status;

	@FindBy(xpath = "//*[contains(@alt, 'Choose from calendar')]")
	public static WebElement ClickonCalendar;

	@FindBy(xpath = "//a[@id='applyLink']")
	public static WebElement ClickOnApply;

	@FindBy(xpath = "//input[@name='$PpyWorkPage$pEventDate']")
	public static WebElement GetDateandTime;

	@FindBy(xpath = "//button[contains(text(), 'Submit')]")
	public static WebElement Submit;

	@FindBy(xpath = "(//button[contains(text(), 'Go')])")
	public static WebElement ClickonGo;

	@FindBy(xpath = "(//span[contains(text(), 'Arrive At Pickup')]/following::div//span)[1]")
	public static WebElement Arrived_At_Pickup_by_Descartes;

	@FindBy(xpath = "(//span[contains(text(), 'Depart At Pickup')]/following::div//span)[1]")
	public static WebElement Depart_At_Pickup_by_Descartes;

	@FindBy(xpath = "(//*[contains(@alt, 'Choose from calendar')])[1]")
	public static WebElement ClickonCalendar_POD_review1;

	@FindBy(xpath = "(//*[contains(@alt, 'Choose from calendar')])[2]")
	public static WebElement ClickonCalendar_POD_review2;

	@FindBy(xpath = "//*[contains(@alt, 'Choose from calendar')]")
	public static WebElement ClickonCalendar_POD_review;

	@FindBy(xpath = "//select[@name='$PpyWorkPage$pAssignedTradingPartnerName']")
	public static WebElement Release__Carrier;

	@FindBy(xpath = "(//button[@name='CaseActionHeader_pyWorkPage_4'])[2]")
	public static WebElement Actions_Button;

	@FindBy(xpath = "//span[contains(text(), 'Refresh')]")
	public static WebElement Actions_Refresh;

	// **** POD Exception Date****
	@FindBy(xpath = "//div[@id='pyFlowActionHTML']//div[contains(@data-ui-meta, 'ArrivedAtPickup')]//div[@class='field-item dataValueRead']//span")
	public static WebElement Arrived_At_Pickup_Date_POD_Exception;

	@FindBy(xpath = "//div[@id='pyFlowActionHTML']//div[contains(@data-ui-meta, 'OutForDelivery')]//div[@class='field-item dataValueRead']//span")
	public static WebElement Depart_At_Pickup_Date_POD_Exception;

	// **** Clip board****
	@FindBy(xpath = "//button[@title='Toggle runtime toolbar']//i[contains(@class,'gear')]")
	public static WebElement Toggle_toolbar;

	@FindBy(xpath = "//button[@title='Clipboard']")
	public static WebElement CLickOnClipBoard;

	@FindBy(xpath = "((//span[contains(@title, 'pyWorkPage')])[1]/ancestor::li)[2]//ul//li//div//a")
	public static WebElement ClickonpyworkPage;

	@FindBy(xpath = "(//span[contains(text(), 'pyWorkPage')])[1]/ancestor::li[2]/ul/li/div/div[1]")
	public static WebElement ExpandpyworkPage;

	@FindBy(xpath = "((//span[contains(text(), 'OrderPage')])[1]/ancestor::li)[3]/ul/li/div/div/a")
	public static WebElement ExpandOrderPageinClipboard;

	@FindBy(xpath = "//span[contains(text(), 'OrderPage')]")
	public static WebElement ClickonOrderpageinCipboard;

	@FindBy(xpath = "(//a[contains(text(), 'ArrivedAtPickup')]/ancestor::tr)[3]//td[2]/div/span")
	public static WebElement Arrived_At_PickUp_Date_Clipboard;

	@FindBy(xpath = "(//a[contains(text(), 'OutForDelivery')]/ancestor::tr)[3]//td[2]/div/span")
	public static WebElement OutForDelivery_Date_Clipboard;

	@FindBy(xpath = "(//h3[@class='layout-group-item-title'])[3]")
	public static WebElement CLickonDataType;

	@FindBy(xpath = "//a[@aria-label='menu Order']")
	public static WebElement ClickonOrderDatatype;

	@FindBy(xpath = "//table[@id='gridLayoutTable']//tbody//tr//td[@data-attribute-name='ArrivedAtPickup']//div")
	public static WebElement Arrived_At_PickUp_Date_Order_Datetype;

	@FindBy(xpath = "//table[@id='gridLayoutTable']//tbody//tr//td[@data-attribute-name='OutForDelivery']//div")
	public static WebElement Out_For_Delivery_Date_Order_Datetype;

	@FindBy(xpath = "//a[@aria-label='menu Status']")
	public static WebElement ClickonStatusDatatype;

	@FindBy(xpath = "//div[@data-lg-child-id='4']/div[@aria-label='Records']/h3[@class='layout-group-item-title']")
	public static WebElement Clickon_Record_Status_and_OrderDatatype;

	@FindBy(xpath = "(//input[@placeholder='Search...'])[2]")
	public static WebElement Searchon_Order_Status_Datatype;

	@FindBy(xpath = "//i[@class='pi pi-search']")
	public static WebElement SelectSearchon_Order_Status_Datatype;

	@FindBy(xpath = "//td[@data-attribute-name='Status Event']//div[contains(text(), ' Received')]")
	public static WebElement Received_Status_in_Status_Datatype;

	@FindBy(xpath = "//td[@data-attribute-name='Status Event']//div[contains(text(), ' Released')]")
	public static WebElement Released_Status_in_Status_Datatype;

	@FindBy(xpath = "//td[@data-attribute-name='Status Event']//div[contains(text(), ' Arrived at Pickup')]")
	public static WebElement Arrived_At_pickup_Status_in_Status_Datatype;

	@FindBy(xpath = "//td[@data-attribute-name='Status Event']//div[contains(text(), ' Out For Delivery')]")
	public static WebElement Out_For_Delivery_Status_in_Status_Datatype;

	// **** Dev Studio Search ****

	@FindBy(xpath = "//input[@name='$PpyDisplayHarness$ppySearchText']")
	public static WebElement ClickDevStudioSearchBox;

	@FindBy(xpath = "//i[@class='pi pi-search-2']")
	public static WebElement ClickDevStudioSearchIcon;

	// **** Searching Order Service Package(Order Creation) in Dev Studio Search****

	@FindBy(xpath = "//a[contains(text(),'OrderServicePackage Services ProcessData')]")
	public static WebElement ClickOrderServicePackage;

	@FindBy(xpath = "//button[contains(text(),'Actions')]")
	public static WebElement ServicePageActions;

	@FindBy(xpath = "(//span[contains(text(),'Run')])[2]")
	public static WebElement ServicePageRun;

	@FindBy(xpath = "//input[@value='EnterText']")
	public static WebElement SupplySOAPCheckBox;

	@FindBy(xpath = "//textarea[@name='$PpySimulationDataPage$ppyRequestTextData']")
	public static WebElement SupplySOAPTextBox;

	@FindBy(xpath = "//div//span[contains(text(),'Execute')]")
	public static WebElement ExecuteClick;

	public static void frameSwitch() {
		driver.switchTo().frame(frameName);

	}

	public static void frameswitch1() {
		driver.switchTo().frame(frameName1);
	}

	public static void frameswitch2() {
		driver.switchTo().frame(frameName2);
	}

	public void PEGALogin() throws InterruptedException {
		ssoLogin.click();
		waits(code);
		code.click();

		// ****Scanner class to handle OTP****
		String scanner = scanner();
		send.sendKeys(scanner);
		waits(click);
		click.click();
		extentTest.log(Status.PASS, "Logged into PEGA Successfully");
	}

	public void LaunchWarehousePortal() throws InterruptedException {
		Await();
		LaunchPortal.click();
		Await();
		warehouse.click();
		Await();
		Windows();
		extentTest.log(Status.PASS, "Enetered into Warehouse Portal");
	}

	public void OrdersPAR() throws Exception {
		Await();
		OrdersPAR.click();
		extentTest.log(Status.PASS, "Entering into Orders PAR");
		Await();
		frameSwitch();
		waits(OSD);
		OSD.click();
		waits(InboundTrailer);

	}

	public void InboundTrailer_WorkQueue() throws InterruptedException {
		InboundTrailer.click();
		Await();
		OrderFilter.click();
		waits(OrderSearch_Filter);
		OrderSearch_Filter.sendKeys(sendkeys);
//		waits(FilterCheckBox);
//		FilterCheckBox.click();
		waits(OrderFilterApply);
		OrderFilterApply.click();
		Await();

		ClickPARCaseID.click();
		Await();
		driver.switchTo().defaultContent();
		driver.switchTo().frame(frameName1);

		Await();
		SelectClass(Inbound_trailer_outboundLoads_Status, "Received");
		ClickonCalendar.click();
		Calendarss();
		Await();
		Submit.click();
		driver.switchTo().defaultContent();
		frameswitch1();

	}

	public void Received_Status() {
		SelectClass(Inbound_trailer_outboundLoads_Status, "Received");
		extentTest.log(Status.PASS, "Order is Moved into Received Status");

	}

	public void SwitchtoOrderPage() {

		WebElement switchtoOrder = driver
				.findElement(By.xpath("//table[@id='RULE_KEY']/tbody/tr/td/span[contains(text(),'" + sendkeys + "')]"));
		switchtoOrder.click();

	}

	public static void getAttribute() {
		DateTime = GetDateandTime.getAttribute("data-value");
	}

	public void DevStudioSearchBox() throws Exception {
		Await();
		Windows();
		Await();
		ClickDevStudioSearchBox.click();
		ClickDevStudioSearchBox.sendKeys(SearchBox_Text);
		ClickDevStudioSearchIcon.click();
		Await();
		ClickOrderServicePackage.click();
		Await();
		frameSwitch();
		ServicePageActions.click();
		Await();
		ServicePageRun.click();
		Await();
	}

	public void SOAPServicePopup() throws Exception {
		Await();
		Windows();
		Await();
		SupplySOAPCheckBox.click();
		Await();
		SupplySOAPTextBox.clear();
		Await();

	}

	public void XML_SendKeys() {

		WebElement textArea = driver
				.findElement(By.xpath("//textarea[@name='$PpySimulationDataPage$ppyRequestTextData']"));
		String filePath = "C:\\Users\\kumarar\\eclipse-workspace\\Sprint_40\\Descartes.xml";
		textArea.sendKeys(new File(filePath).getAbsolutePath());

	}

	public static void ClickExecute() throws Exception {
		Await();
		ScrollDown2();
		ExecuteClick.click();
		Await();
		// Windows();
		// driver.close();
//		Await();
		ArrayList<String> tab = new ArrayList<>(driver.getWindowHandles());
		// driver.switchTo().window(tab.get(0));
		// Await();
		driver.switchTo().window(tab.get(1));

//		String parentWindow = driver.getWindowHandle();
//		driver.switchTo().window(parentWindow);
//		Windows();

	}

	public void XML_Write() {
		try {
			// Load the XML document
			File xmlFile = new File("C:\\Users\\kumarar\\eclipse-workspace\\Sprint_40\\Descartes.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);

			// Normalize the document to ensure proper structure
			doc.getDocumentElement().normalize();

			String XML_tags[] = { "MLW", "Job", "Extension" };

			String Values[] = { "0", "2", "3", "4", "6", "7", "8" };

			NodeList Soap_Body = doc.getElementsByTagName(XML_tags[0]);

			for (int i = 0; i < Soap_Body.getLength(); i++) {
				Element body = (Element) Soap_Body.item(i);
				body.setAttribute("Cmd", Values[5]);

				// orderRefs.put("BOL", Math.ceil(Math.random() * 100000000));

				// body.setAttribute("cmd" , Math.ceil(Math.random() * 100000000));
				body.setAttribute("SType", Values[0]);
				String currentDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date());
				body.setAttribute("TStamp", currentDate);
			}

			NodeList Soap_Body2 = doc.getElementsByTagName(XML_tags[1]);
			for (int i = 0; i < Soap_Body2.getLength(); i++) {
				Element body = (Element) Soap_Body2.item(i);
				body.setAttribute("JType", Values[1]);
				body.setAttribute("Status", Values[2]);

			}

			NodeList Soap_Body3 = doc.getElementsByTagName(XML_tags[2]);
			for (int i = 0; i < Soap_Body3.getLength(); i++) {
				Element body = (Element) Soap_Body3.item(i);
				body.setAttribute("Value", sendkeys);
				System.out.println(body);

			}

			// Write the updated document back to the same file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(xmlFile);
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);

			System.out.println("XML file updated successfully.");

		} catch (ParserConfigurationException | SAXException | IOException | TransformerException e) {
			e.printStackTrace();
		}

	}

	public void After_XML_hit() throws InterruptedException {
		driver.switchTo().defaultContent();
		frameswitch1();
		Await();
		ClickonGo.click();
		Await();
		driver.switchTo().defaultContent();
		frameswitch1();
		Await();
		SelectClass(Inbound_trailer_outboundLoads_Status, "Released");
		Await();
		ClickonCalendar.click();
		Calendarss();
		Await();
		Submit.click();
		Await();
		ClickonGo.click();
		Await();
		driver.switchTo().defaultContent();
		frameswitch1();

	}

	public void POD_Exception() throws InterruptedException {

		if (Arrived_At_Pickup_by_Descartes.isDisplayed()) {
			ClickonCalendar_POD_review.click();
			Calendarss();
		} else if (Depart_At_Pickup_by_Descartes.isDisplayed()) {
			ClickonCalendar_POD_review.click();
			Calendarss();
			// ClickonCalendar_POD_review2.click();
		} else {
			ClickonCalendar_POD_review1.click();
			Calendarss();
			Await();
			ClickonCalendar_POD_review2.click();
			Calendarss();
		}

//		ClickonCalendar_POD_review.click();
//		Calendarss();
		Await();
//		ClickonCalendar_POD_review2.click();
//		Calendarss();
		Await();
		SelectClass(Release__Carrier, "Radiant");
		Await();
		Submit.click();
		extentTest.log(Status.PASS, "Enterng into POD Exception Screen");
	}

	public void POD_Exception_2() throws InterruptedException {
		driver.switchTo().defaultContent();
		frameswitch1();
		Await();
		ClickonGo.click();
		Await();
		driver.switchTo().defaultContent();
		frameswitch1();
		Await();
		getIgnoringStaleElementException(Arrived_At_Pickup_Date_POD_Exception);
		Arrived_POD = Arrived_At_Pickup_Date_POD_Exception.getText();
		System.out.println("Arrived_At_Pickup_Date_POD_Exception :" + Arrived_POD);
		Await();
		Depart_POD = Depart_At_Pickup_Date_POD_Exception.getText();
		System.out.println("Depart_At_Pickup_Date_POD_Exception :" + Depart_POD);
	}

	public void Clip_board() throws InterruptedException, ParseException {
		driver.switchTo().defaultContent();
		Await();
		Toggle_toolbar.click();
		Await();
		CLickOnClipBoard.click();
		Await();
		Windows();
		Await();
		ScrollDown();
		// Await();
		// ClickonpyworkPage.click();
		Await();
		ExpandpyworkPage.click();
		Await();
		ExpandOrderPageinClipboard.click();
		Thread.sleep(9000);
		ClickonOrderpageinCipboard.click();
		Await();
		String Date_Arrived_Order_Clipboard = Arrived_At_PickUp_Date_Clipboard.getText();
		System.out.println("Arrived_At_PickUp_Date_Clipboard :" + Date_Arrived_Order_Clipboard);

		Await();
		SimpleDateFormat formats1 = new SimpleDateFormat("M/d/yy h:mm a");
		Date dates1 = formats1.parse(Arrived_POD);

		SimpleDateFormat formats2 = new SimpleDateFormat("yyyyMMdd'T'HHmmss.SSS z");
		Date dates2 = formats2.parse(Date_Arrived_Order_Clipboard);

		SimpleDateFormat forms = new SimpleDateFormat("MM/dd/yyyy");
		String dateExpected1 = forms.format(dates2);
		String dateActual1 = forms.format(dates1);

		System.out.println(dateActual1 + "----------------------" + dateExpected1);
		assertEquals(dateActual1, dateExpected1);

		System.out
				.println("Successfully Validated Arrived At Pickup Date in Clip board:" + Date_Arrived_Order_Clipboard);

		extentTest.log(Status.PASS, "Successfully Validated Arrived At Pickup Date in Clipboard");

		//

		Await();
		String OutForDelivery_OrderPage = OutForDelivery_Date_Clipboard.getText();
		System.out.println("OutForDelivery_Date_Clipboard :" + OutForDelivery_OrderPage);

		Await();
		SimpleDateFormat form1 = new SimpleDateFormat("M/d/yy h:mm a");
		Date date1 = form1.parse(Arrived_POD);

		SimpleDateFormat form2 = new SimpleDateFormat("yyyyMMdd'T'HHmmss.SSS z");
		Date date2 = form2.parse(OutForDelivery_OrderPage);

		SimpleDateFormat form = new SimpleDateFormat("MM/dd/yyyy");
		String datesExpected1 = form.format(date2);
		String datesActual1 = form.format(date1);

		System.out.println(datesActual1 + "----------------------" + datesExpected1);
		assertEquals(datesActual1, datesExpected1);

		System.out.println("Successfully Validated Out For Delivery Date in Clipboard:" + OutForDelivery_OrderPage);

		extentTest.log(Status.PASS, "Successfully Validated Out For Delivery Date in Clipboard");
		driver.close();

	}

	public void BackToPega() throws InterruptedException, ParseException {

		Await();
		// waits(CLickonDataType);
		// Windows();
//	//	String parentWindow = driver.getWindowHandle();
//		driver.switchTo().window(parentWindow);

		ArrayList<String> tab = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(tab.get(0));
		// Windows();

		CLickonDataType.click();
		Await();
		ClickonOrderDatatype.click();

		driver.switchTo().defaultContent();
		// FrameSwtich4();
		frameswitch2();
		Await();
		// waits(Clickon_Record_Status_and_OrderDatatype);
		// Await();
		Thread.sleep(9000);
		Clickon_Record_Status_and_OrderDatatype.click();
		waits(Searchon_Order_Status_Datatype);
		Searchon_Order_Status_Datatype.click();
		Searchon_Order_Status_Datatype.sendKeys(sendkeys);
		waits(SelectSearchon_Order_Status_Datatype);
		SelectSearchon_Order_Status_Datatype.click();
		Await();

		String Date_Arrived_Order_Datatype = Arrived_At_PickUp_Date_Order_Datetype.getText();

		SimpleDateFormat formats1 = new SimpleDateFormat("M/d/yy h:mm a");
		Date dates1 = formats1.parse(Arrived_POD);

		SimpleDateFormat formats2 = new SimpleDateFormat("M/d/yyyy h:mm a");
		Date dates2 = formats2.parse(Date_Arrived_Order_Datatype);

		SimpleDateFormat forms = new SimpleDateFormat("MM/dd/yyyy");
		String dateExpected1 = forms.format(dates2);
		String dateActual1 = forms.format(dates1);

		System.out.println(dateActual1 + "----------------------" + dateExpected1);
		assertEquals(dateActual1, dateExpected1);

		System.out.println("Successfully Validated Arrived At Pickup Date in datatype:" + Date_Arrived_Order_Datatype);

		extentTest.log(Status.PASS, "Successfully Validated Arrived At Pickup Date in datatype");

		String Date_Depart_Order_Datatype = Out_For_Delivery_Date_Order_Datetype.getText();

		SimpleDateFormat Date_format1 = new SimpleDateFormat("M/d/yy h:mm a");
		Date Date1 = Date_format1.parse(Arrived_POD);

		SimpleDateFormat Date_format2 = new SimpleDateFormat("M/d/yyyy h:mm a");
		Date Date2 = Date_format2.parse(Date_Arrived_Order_Datatype);

		SimpleDateFormat form = new SimpleDateFormat("MM/dd/yyyy");
		String dateExpected01 = form.format(Date2);
		String dateActual01 = form.format(Date1);

		System.out.println(dateActual01 + "----------------------" + dateExpected01);
		assertEquals(dateActual1, dateExpected1);

		System.out.println("Successfully Validated Out for Delivery Date in datatype:" + Date_Depart_Order_Datatype);
		extentTest.log(Status.PASS, "Successfully Validated Out for Delivery Date in datatype");

		Await();
		driver.switchTo().defaultContent();
		ClickonStatusDatatype.click();
		Await();

		// FrameSwtich3();
		Await();

		Actions PEGA = new Actions(driver);
		PEGA.keyDown(Keys.ARROW_DOWN);
		PEGA.keyUp(Keys.ARROW_DOWN);

		PEGA.keyDown(Keys.ARROW_DOWN);
		PEGA.keyUp(Keys.ARROW_DOWN);

		PEGA.keyDown(Keys.ARROW_DOWN);
		PEGA.keyUp(Keys.ARROW_DOWN);

		PEGA.keyDown(Keys.ARROW_DOWN);
		PEGA.keyUp(Keys.ARROW_DOWN);

		Await();
		driver.switchTo().defaultContent();
		frameswitch1();
		Thread.sleep(9000);
		waits(Clickon_Record_Status_and_OrderDatatype);
		Clickon_Record_Status_and_OrderDatatype.click();
		waits(Searchon_Order_Status_Datatype);
		Searchon_Order_Status_Datatype.click();
		Searchon_Order_Status_Datatype.sendKeys(sendkeys);
		waits(SelectSearchon_Order_Status_Datatype);
		SelectSearchon_Order_Status_Datatype.click();
		Await();

		String Received_Status = Received_Status_in_Status_Datatype.getText();
		assertTrue(Received_Status.contains("Received"));
		System.out.println("Successfully Validated Received_Status:" + Received_Status);
		extentTest.log(Status.PASS, "Successfully Validated Received Status");

		Await();
		String Released_Status = Released_Status_in_Status_Datatype.getText();
		assertTrue(Released_Status.contains("Released"));
		System.out.println("Successfully Validated Released_Status:" + Released_Status);
		extentTest.log(Status.PASS, "Successfully Validated Released Status");

		Await();
		String Arrived_At_Pickup_Status = Arrived_At_pickup_Status_in_Status_Datatype.getText();
		assertTrue(Arrived_At_Pickup_Status.contains("Arrived at Pickup"));
		System.out.println("Successfully Validated Arrived At Pickup status :" + Arrived_At_Pickup_Status);
		extentTest.log(Status.PASS, "Successfully Validated Arrived At Pickup Status");

		Await();
		String Out_For_Delivery_Status = Out_For_Delivery_Status_in_Status_Datatype.getText();
		assertTrue(Out_For_Delivery_Status.contains("Out For Delivery"));
		System.out.println("Successfully Validated Out For Delivery Status:" + Out_For_Delivery_Status);
		extentTest.log(Status.PASS, "Successfully Validated Out for Delivery Status");
	}

}
