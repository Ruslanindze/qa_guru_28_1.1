package qa.guru.data;

import java.util.List;

public final class TestData {
    // все поля
    public final static String FIRST_NAME = "Дебаг";
    public final static String LAST_NAME = "Логов";
    public final static String USER_EMAIL = "please_work_finally@test.ru";
    public final static String USER_GENDER = "Male";
    public final static String USER_NUMBER = "7404500200";
    public final static String YEAR_OF_BIRTH = "1990";
    public final static String MONTH_OF_BIRTH = "April";
    public final static String DAY_OF_BIRTH = "14";
    public final static List<String> SUBJECTS = List.of("Maths", "Economics", "English");
    public final static List<String> HOBBIES = List.of("Sports", "Music");
    public final static String PICTURE = "alan-wake-2.jpg";
    public final static String ADDRESS = "Room 101, Heart o' the City Hotel, 2200 Cyber Avenue";
    public final static String STATE = "Uttar Pradesh";
    public final static String CITY = "Lucknow";

    // только нужные поля
    public final static String FIRST_NAME_2 = "Лариса";
    public final static String LAST_NAME_2 = "Крофтова";
    public final static String USER_GENDER_2 = "Female";
    public final static String USER_NUMBER_2 = "7404500300";

    // некорректные поля
    public final static String USER_EMAIL_BAD = "@please_work_finally@test.ru";
    public final static String USER_NUMBER_BAD = "740450030";

    // поля для проверки reserve
    public final static String FIRST_NAME_3 = "Чупакабра";
    public final static String LAST_NAME_3 = "Редкая";
    public final static String USER_GENDER_3 = "Other";
    public final static String USER_NUMBER_3 = "7404900300";
}
