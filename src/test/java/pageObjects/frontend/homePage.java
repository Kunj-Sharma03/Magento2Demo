package pageObjects.frontend;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pageObjects.frontend.productPage;

public class homePage {
    private WebDriver driver;

    // Constructor to initialize the driver
    public homePage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators for various homepage elements
    private By searchBar = By.id("search"); // Search bar
    private By searchButton = By.xpath("//button[@aria-label='Search']"); // Search button
    private By hotsellers = By.xpath("//h2[normalize-space()='Hot Sellers']"); // Featured products section
    private By bannerImages = By.cssSelector(".blocks-promo .block-promo img"); // Banner images
    private By headerLinks = By.cssSelector(".header.links li a"); // Links in the header
    private By menuCategories = By.cssSelector(".level0 .category-item a"); // Category menu items
    private By newsletterInput = By.id("newsletter"); // Newsletter input
    private By newsletterSubscribeButton = By.cssSelector("button.action.subscribe.primary"); // Subscribe button
    private By footerLinks = By.cssSelector("footer .links a"); // Footer links

    // General UI Methods
    public void enterSearchText(String searchText) {
        driver.findElement(searchBar).sendKeys(searchText);
    }

    public void clickSearchButton() {
        driver.findElement(searchButton).click();
    }

    public boolean verifyFeaturedProductsDisplayed() {
        return driver.findElement(hotsellers).isDisplayed();
    }

    public boolean verifyAllBannersDisplayed() {
        List<WebElement> banners = driver.findElements(bannerImages);
        return banners.stream().allMatch(WebElement::isDisplayed);
    }

    public boolean verifyHeaderLinksDisplayed() {
        List<WebElement> links = driver.findElements(headerLinks);
        return links.size() > 0 && links.get(0).isDisplayed();
    }

    public void clickHeaderLink(String linkText) {
        List<WebElement> links = driver.findElements(headerLinks);
        for (WebElement link : links) {
            if (link.getText().equalsIgnoreCase(linkText)) {
                link.click();
                break;
            }
        }
    }

    public void hoverOverCategory(String categoryName) {
        Actions action = new Actions(driver);
        List<WebElement> categories = driver.findElements(menuCategories);
        for (WebElement category : categories) {
            if (category.getText().equalsIgnoreCase(categoryName)) {
                action.moveToElement(category).perform();
                break;
            }
        }
    }

    public boolean verifyNewsletterSubscription() {
        return driver.findElement(newsletterInput).isDisplayed();
    }

    public void subscribeToNewsletter(String email) {
        driver.findElement(newsletterInput).sendKeys(email);
        driver.findElement(newsletterSubscribeButton).click();
    }

    public boolean verifyFooterLinks() {
        List<WebElement> links = driver.findElements(footerLinks);
        return links.size() > 0 && links.get(0).isDisplayed();
    }

    public void clickFooterLink(String linkText) {
        List<WebElement> links = driver.findElements(footerLinks);
        for (WebElement link : links) {
            if (link.getText().equalsIgnoreCase(linkText)) {
                link.click();
                break;
            }
        }
    }

    // Shopping Cart Methods
    private By loginLink = By.cssSelector("a[href*='customer/account/login']");
    private By cartItemCount = By.cssSelector(".minicart-items");
    private By cartTotalPrice = By.cssSelector(".price");
    private By cartIcon = By.cssSelector(".action.showcart");
    private By featuredProducts = By.xpath("//div[@class='products-grid grid']/ol[@class='product-items widget-product-grid']/li[@class='product-item']");
    private By addToCartButton = By.cssSelector("button[title='Add to Cart']");
    private By addToWishlistButton = By.cssSelector("button[title='Add to Wishlist']");

    public void clickLoginLink() {
        driver.findElement(loginLink).click();
    }

    public void clickCartIcon() {
        driver.findElement(cartIcon).click();
    }

    public void verifyCartDetails() {
        String itemCount = driver.findElement(cartItemCount).getText();
        String totalPrice = driver.findElement(cartTotalPrice).getText();
        System.out.println("Items in Cart: " + itemCount);
        System.out.println("Total Price: " + totalPrice);
    }

    // Hover over a featured product
    private void hoverOverFeaturedProduct(int index) {
        List<WebElement> products = driver.findElements(featuredProducts);
        if (index < products.size()) {
            Actions action = new Actions(driver);
            action.moveToElement(products.get(index)).perform();
        }
    }

    // Add featured product to cart (first 4 products with size/color selection)
    public void addFeaturedProductToCart(int index, String size, String color) {
        hoverOverFeaturedProduct(index); // Hover first

        List<WebElement> products = driver.findElements(featuredProducts);
        if (index < products.size()) {
            WebElement product = products.get(index);
            // Select size and color if applicable
            if (index < 4) { // First 4 products
                productPage productPage = new productPage(driver); // Use correct capitalization
                productPage.selectSizeAndColorAndAddToCart(size, color);
            } else {
                // Directly click add to cart for products beyond the first 4
                product.findElement(addToCartButton).click();
            }
        }
    }

    // Add featured product to wishlist
    public void addFeaturedProductToWishlist(int index) {
        hoverOverFeaturedProduct(index); // Hover first

        List<WebElement> products = driver.findElements(featuredProducts);
        if (index < products.size()) {
            WebElement product = products.get(index);
            product.findElement(addToWishlistButton).click();
        }
    }
}


