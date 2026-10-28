package qa.guru.data;

import net.datafaker.Faker;

import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public final class TestData {
    private static final String DEFAULT_PICTURE = "alan-wake-2.jpg";
    private static final Faker FAKER_RU = new Faker(new Locale("ru"));
    private static final Faker FAKER_EN = new Faker(new Locale("en"));

    public static UserDto getRegistrationUser() {
        // Рандомное имя
        String fullName = FAKER_RU.name().fullName();
        String[] nameParts = fullName.split(" ");


        // Рандомный день рождения от 18 до 65 лет.
        var birthday = FAKER_RU.timeAndDate().birthday(18, 70);
        String birthYear = String.valueOf(birthday.getYear());
        String birthMoth = birthday.getMonth()
                .getDisplayName(TextStyle.FULL_STANDALONE, Locale.ENGLISH);
        String birthDay = String.valueOf(birthday.getDayOfMonth());

        // Рандомный штат и город
        State randomState = FAKER_RU.options().option(State.class);
        String randomCity = FAKER_RU.options().nextElement(randomState.getCities());

        return UserDto.builder()
                .withFirstName(nameParts[0])
                .withLastName(nameParts[1])
                .withEmail(FAKER_EN.internet().emailAddress(FAKER_EN.credentials().username()))
                .withGender(FAKER_RU.options().option(Gender.class).getValue())
                .withPhoneNumber(FAKER_RU.number().digits(10))
                .withBirthYear(birthYear)
                .withBirthMonth(birthMoth)
                .withBirthDay(birthDay)
                .withSubjects(getRandomSubjects())
                .withHobbies(getRandomHobbies())
                .withPicture(DEFAULT_PICTURE)
                .withCurrentAddress(FAKER_RU.address().fullAddress())
                .withState(randomState.getState())
                .withCity(randomCity)
                .build();
    }

    public static UserDto getRegistrationUserShort() {
        // Рандомное имя
        String fullName = FAKER_RU.name().fullName();
        String[] nameParts = fullName.split(" ");

        return UserDto.builder()
                .withFirstName(nameParts[0])
                .withLastName(nameParts[1])
                .withGender(FAKER_RU.options().option(Gender.class).getValue())
                .withPhoneNumber(FAKER_RU.number().digits(10))
                .build();
    }

    public static UserDto getRegistrationUserWithBadEmail() {
        UserDto user = getRegistrationUserShort();

        String badEmail = "@" + FAKER_EN.internet().emailAddress(FAKER_EN.credentials().username());
        user.setEmail(badEmail);

        return user;
    }

    private static List<String> getRandomSubjects() {
        List<String> shuffledSubjects = Subject.getValues();
        int countSubjects = FAKER_RU.number().numberBetween(1, Math.min(5, shuffledSubjects.size()));

        return shuffledSubjects.subList(0, countSubjects);
    }

    private static List<String> getRandomHobbies() {
        List<String> shuffledHobbies = Hobby.getValues();
        int countSubjects = FAKER_RU.number().numberBetween(1, shuffledHobbies.size());

        return shuffledHobbies.subList(0, countSubjects);
    }
}
