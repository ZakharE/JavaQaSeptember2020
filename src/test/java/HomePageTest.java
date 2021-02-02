import io.qameta.allure.Feature;
import io.qameta.allure.Flaky;
import org.testng.annotations.Test;
import pages.AuthPage;

import static constants.SocialNetworks.TG;
import static constants.SocialNetworks.VK;


public class HomePageTest extends BaseTest {
    @Test(description = "Check that info saved correctly in 'About' section")
    @Feature("Personal cabinet")
    @Flaky
    public void openHomePage() {
        AuthPage authPage = new AuthPage(driver);
        String city = "Санкт-Петербург";
        String country = "Россия";
        String englishLevel = "Начальный уровень (Beginner)";
        String workSchedule = "Полный день";
        String lastName = "Test";
        String company = "Компания";
        String position = "Должность";
        String gender = "Мужской";

        authPage
                .clickLoginButton()
                .signUp(UserConfig.LOGIN, UserConfig.PASSWORD)
                .goToAboutSection()
                .fillLastName(lastName)
                .selectCountry(country)
                .selectCity(city)
                .selectEnglishLevel(englishLevel)
                .selectWorkSchedule(workSchedule)
                .selectGender(gender)
                .inputCompany(company)
                .inputCompanyPosition(position)
                .save();
        driver.close();

        driver = createNewSession();
        authPage = new AuthPage(driver);
        authPage
                .clickLoginButton()
                .signUp(UserConfig.LOGIN, UserConfig.PASSWORD)
                .goToAboutSection()
                .assertLastNameIs(lastName)
                .assertCityIs(city)
                .assertCountryIs(country)
                .assertEnglishLevelIs(englishLevel)
                .assertWorkScheduleIs(workSchedule)
                .assertCompanyIs(company)
                .assertGenderIs(gender)
                .assertCompanyPositionIs(position)
                .assertHasContact(1, VK)
                .assertHasContact(0, TG);
    }

    @Test(description = "Login in cabinet")
    @Feature("Personal cabinet")
    public void login() {
        AuthPage authPage = new AuthPage(driver);
        authPage
                .clickLoginButton()
                .signUp(UserConfig.LOGIN, UserConfig.PASSWORD)
                .assertLoggedInAs("dsa");
    }
}
