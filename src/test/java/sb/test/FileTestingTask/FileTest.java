package sb.test.FileTestingTask;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import org.junit.Assert;
import cucumber.api.java.Before;
import cucumber.api.java.en.*;

public class FileTest {

	private static  String URL_STRING = "";
	
	//Находим файл конфигурации
	@Before
	public void findConfigFile() throws Throwable {
		Assert.assertNotNull("Config file not found", getFileFromAddress("/config.properties"));
	}

	// Проверяем наличие пути до тестового файла в конфиге
	@Given("^Find address in configuration file$")
	public void readConfigFile() throws Throwable {
		Properties prop = new Properties();
		InputStream inputStream = getFileFromAddress("/config.properties");
		prop.load(inputStream);
		if (prop.getProperty("URL") != null) {
			URL_STRING = prop.getProperty("URL");
			System.out.println(URL_STRING);
			}
		else {
			Assert.fail("There is no address in config file");
		}
	}

	// Находим тестовый файл
	@When("^We can read testing file$")
	public void readTestFile() throws Throwable {
		Assert.assertNull("Test file not found", getFileFromAddress(URL_STRING));
	}
	
	//Считываем тестовый файл и сравниваем строки
	@Then("^Check row ([^\"]*) for ([^\"]*) equals$")
	public void compareRows(int arg1, String arg2) throws Throwable {
		List<String> rows = Files.readAllLines(Paths.get(URL_STRING));
		if (rows.size() > arg1){
			Assert.assertEquals("Comparing rows: ", arg2, rows.get(arg1));
		} else {
			Assert.fail("There is no row #" + arg1);
		}
	}

	public InputStream getFileFromAddress(String address){
		return getClass().getResourceAsStream(address);
	}
}
