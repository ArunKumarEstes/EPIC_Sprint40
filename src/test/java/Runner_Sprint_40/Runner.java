package Runner_Sprint_40;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import Sprint_40.EPIC_1978;
import Sprint_40.EPIC_2053;
import Sprint_40.UtilClass;

public class Runner extends UtilClass {

	@Test
	public static void EPICPLTFRM_1978() throws Exception {
		EPIC_1978 login = new EPIC_1978(driver);
		login.PAR_Order_Creation();
		login.PEGALogin();
		login.LaunchWarehousePortal();
		login.OrdersPAR();

	}

	@Test
	public void EPICPLTFRM_2053() throws Exception {
		EPIC_2053 login = new EPIC_2053(driver);
		login.PAR_Order_Creation();
		login.PEGALogin();
		login.LaunchWarehousePortal();
		login.OrdersPAR();
		login.InboundTrailer_WorkQueue();
		login.XML_Write();

		login.DevStudioSearchBox();
		login.SOAPServicePopup();
		WebElement textArea = driver
				.findElement(By.xpath("//textarea[@name='$PpySimulationDataPage$ppyRequestTextData']"));
		String filePath = "C:\\Users\\kumarar\\eclipse-workspace\\Sprint_40\\Descartes.xml";
		// login.After_XML_hit();
		login.sendKeysJavascript(driver, textArea, filePath);
		// login.XML_SendKeys();
		EPIC_2053.ClickExecute();
		login.After_XML_hit();
		login.POD_Exception();
		login.POD_Exception_2();
		login.Clip_board();
		login.BackToPega();

	}

}
