package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class SignupPage extends BasePage {

    // Account Information
    private By titleMr = By.id("id_gender1");
    private By titleMrs = By.id("id_gender2");

    private By nameInput = By.id("name");
    private By emailInput = By.id("email");
    private By passwordInput = By.id("password");

    private By dayDropdown = By.id("days");
    private By monthDropdown = By.id("months");
    private By yearDropdown = By.id("years");

    private By newsletterCheckbox = By.id("newsletter");
    private By specialOffersCheckbox = By.id("optin");

    // Address Information
    private By firstNameInput = By.id("first_name");
    private By lastNameInput = By.id("last_name");
    private By companyInput = By.id("company");
    private By addressInput = By.id("address1");
    private By address2Input = By.id("address2");

    private By countryDropdown = By.id("country");
    private By stateInput = By.id("state");
    private By cityInput = By.id("city");
    private By zipcodeInput = By.id("zipcode");
    private By mobileInput = By.id("mobile_number");

    private By createAccountButton = By.cssSelector("button[data-qa='create-account']");
    private By accountInformationTitle = By.xpath("//b[contains(text(),'Enter Account Information')]");

    public SignupPage(WebDriver driver) {
        super(driver);
    }

    public void selectTitleMr() {
        click(titleMr);
    }

    public void selectTitleMrs() {
        click(titleMrs);
    }

    public void enterName(String name) {
        type(nameInput, name);
    }

    public void enterEmail(String email) {
        type(emailInput, email);
    }

    public void enterPassword(String password) {
        type(passwordInput, password);
    }

    public void selectDateOfBirth(String day, String month, String year) {
        new Select(driver.findElement(dayDropdown)).selectByValue(day);
        new Select(driver.findElement(monthDropdown)).selectByValue(month);
        new Select(driver.findElement(yearDropdown)).selectByValue(year);
    }

    public void subscribeToNewsletter() {
        click(newsletterCheckbox);
    }

    public void acceptSpecialOffers() {
        click(specialOffersCheckbox);
    }

    public void enterFirstName(String firstName) {
        type(firstNameInput, firstName);
    }

    public void enterLastName(String lastName) {
        type(lastNameInput, lastName);
    }

    public void enterCompany(String company) {
        type(companyInput, company);
    }

    public void enterAddress(String address) {
        type(addressInput, address);
    }

    public void enterAddress2(String address2) {
        type(address2Input, address2);
    }

    public void selectCountry(String country) {
        new Select(driver.findElement(countryDropdown))
                .selectByVisibleText(country);
    }

    public void enterState(String state) {
        type(stateInput, state);
    }

    public void enterCity(String city) {
        type(cityInput, city);
    }

    public void enterZipcode(String zipcode) {
        type(zipcodeInput, zipcode);
    }

    public void enterMobileNumber(String mobile) {
        type(mobileInput, mobile);
    }

    public AccountPage clickCreateAccount() {
        click(createAccountButton);
        return new AccountPage(driver);
    }
    public boolean isAccountInformationDisplayed() {
        return isDisplayed(accountInformationTitle);
    }

    public void fillAccountInformation(
            String name,
            String email,
            String password,
            String day,
            String month,
            String year
    ) {
        selectTitleMr();
        enterName(name);
        enterEmail(email);
        enterPassword(password);
        selectDateOfBirth(day, month, year);
    }

    public void fillAddressInformation(
            String firstName,
            String lastName,
            String company,
            String address,
            String address2,
            String country,
            String state,
            String city,
            String zipcode,
            String mobile
    ) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterCompany(company);
        enterAddress(address);
        enterAddress2(address2);
        selectCountry(country);
        enterState(state);
        enterCity(city);
        enterZipcode(zipcode);
        enterMobileNumber(mobile);
    }
}
