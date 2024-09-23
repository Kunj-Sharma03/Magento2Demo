package pageObjects.frontend;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class productPage {
	 private WebDriver driver;

	    // Constructor to initialize the driver

    public productPage(WebDriver driver) {
    	this.driver = driver;
		}

	// Selectors
    private By sizeSelector = By.cssSelector(".swatch-option.text"); // Base selector for size options
    private By colorSelector = By.cssSelector(".swatch-option.color"); // Base selector for color options
    private By addToCartButton = By.cssSelector("button[title='Add to Cart']");

    // Method to select size
    public void selectSize(String size) {
        WebElement sizeOption = driver.findElement(By.xpath("//div[@class='swatch-option text' and @aria-label='" + size + "']"));
        sizeOption.click();
    }

    // Method to select color
    public void selectColor(String color) {
        WebElement colorOption = driver.findElement(By.xpath("//div[@class='swatch-option color' and @aria-label='" + color + "']"));
        colorOption.click();
    }

    // Method to add product to cart
    public void addToCart() {
        driver.findElement(addToCartButton).click();
    }

    // Combined method to select size, color, and add to cart
    public void selectSizeAndColorAndAddToCart(String size, String color) {
        selectSize(size);
        selectColor(color);
        addToCart();
    }
}

