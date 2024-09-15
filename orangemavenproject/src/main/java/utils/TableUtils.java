package utils;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TableUtils {
    public static int getNumberOfRows(WebDriver driver, String tableXpath){
        String rowLocator = String.format("%s/tbody/tr",tableXpath);
		return driver.findElements(By.xpath(rowLocator)).size();
    }
    public static List<String> getHeaderData(WebDriver driver, String tableXpath){
        List<String> ColHeadings=new ArrayList<String>();
		String cellLocator = String.format("%s//th",tableXpath);
		List<WebElement> colWebElements=driver.findElements(By.xpath(cellLocator));
		for(int i=0;i<colWebElements.size();i++) {
			ColHeadings.add(colWebElements.get(i).getText());
		}
		return ColHeadings;
    }
	public static String getRowData(WebDriver driver, String tableXpath, String rowIndex){
		String rowLocator = String.format("%s/tbody/tr[/*][%s]",tableXpath,rowIndex.trim());
		return driver.findElement(By.xpath(rowLocator)).getText().trim().replaceAll("[\\t\\n\\r]+"," ");
	}
	public static List<String> getColumnData(WebDriver driver, String tableXpath, String columnIndex){
		ArrayList<String> columnData = new ArrayList<>();
		for(int i=1;i<=getNumberOfRows(driver,tableXpath);i++){
		String columnLocator=String.format("%s/tbody/tr[%s]/td[%s]",tableXpath,i,columnIndex.trim());
		String columnText = driver.findElement(By.xpath(columnLocator)).getText().trim().replaceAll("[\\t\\n\\r]+"," ");
		columnData.add(columnText);
		}
		return columnData;
	}
	public static String getDataFromCell(WebDriver driver, String tableXpath, String rowIndex, String columnIndex){
		String cellLocator=String.format("%s/tbody/tr[%s]/td[%s]",tableXpath,rowIndex.trim(),columnIndex.trim());
		return driver.findElement(By.xpath(cellLocator)).getText().trim().replaceAll("[\\t\\n\\r]+"," ");
	}
	public static void clickDataInCell(WebDriver driver, String tableXpath, String rowIndex, String columnIndex){
		String cellLocator=String.format("%s/tbody/tr[%s]/td[%s]",tableXpath,rowIndex.trim(),columnIndex.trim());
		driver.findElement(By.xpath(cellLocator)).click();
	}
	public static void clickCellBasedOnanotherCell(WebDriver driver, String tableXpath, String columnIndex, String Data, String columnIndexToBeClicked){
		for(int i=1;i<=getNumberOfRows(driver,tableXpath);i++){
			String columnLocator=String.format("%s/tbody/tr[%s]/td[%s]",tableXpath,i,columnIndex.trim());
			String columnText = driver.findElement(By.xpath(columnLocator)).getText().trim().replaceAll("[\\t\\n\\r]+"," ");
			if(columnText.equals(Data)){
				String cellLocator=String.format("%s/tbody/tr[%s]/td[%s]",tableXpath,i,columnIndexToBeClicked.trim());
				driver.findElement(By.xpath(cellLocator)).click();
			}
		}
	}
}